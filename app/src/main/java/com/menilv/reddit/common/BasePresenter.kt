package com.menilv.reddit.common

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import com.menilv.reddit.presenter.Presenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import io.reactivex.subjects.UnicastSubject

abstract class BasePresenter<V, VS, FVS>(private val startWithInitialState: Boolean = false) : Presenter where V : BaseView<FVS> {
  private val viewStateBehaviorSubject: BehaviorSubject<FVS> = BehaviorSubject.create()
  private val intentRelaysBinders = ArrayList<IntentRelayBinderPair<*>>(4)
  private var subscribeViewStateMethodCalled = false
  private var intentDisposables: CompositeDisposable? = null
  var actionDisposable: CompositeDisposable? = null
  private var viewRelayConsumerDisposable: Disposable? = null
  private var viewStateDisposable: Disposable? = null
  private var viewAttachedFirstTime = true
  private var viewStateConsumer: ((V, FVS) -> Unit)? = null
  protected val viewStateObservable: Observable<FVS>
    get() = viewStateBehaviorSubject

  private inner class IntentRelayBinderPair<I>(
      internal val intentRelaySubject: Subject<I>,
      internal val intentBinder: (V) -> Observable<I>
  )

  init {
    reset()
  }

  @CallSuper
  override fun attachView(view: BaseView<*>) {
    if (viewAttachedFirstTime) {
      bindIntents()
    }
    if (viewStateConsumer != null) {
      subscribeViewStateConsumerActually(view as V)
    }
    val intentsSize = intentRelaysBinders.size
    for (i in 0 until intentsSize) {
      val intentRelayBinderPair = intentRelaysBinders[i]
      bindIntentActually<Any>(view as V, intentRelayBinderPair)
    }
    viewAttachedFirstTime = false
  }

  @CallSuper
  override fun detachView() {
    // Cancel subscription from View to viewState Relay
    viewRelayConsumerDisposable?.dispose()
    viewRelayConsumerDisposable = null
    // Cancel subscriptions from view intents to intent Relays
    intentDisposables?.dispose()
    intentDisposables = null
    actionDisposable?.dispose()
    actionDisposable = null
  }

  @CallSuper
  override fun destroy() {
    if (viewStateDisposable != null) {
      // Cancel the overall observable stream
      viewStateDisposable?.dispose()
    }
    unbindIntents()
    reset()
  }

  private fun reset() {
    viewAttachedFirstTime = true
    intentRelaysBinders.clear()
    subscribeViewStateMethodCalled = false
  }

  protected fun subscribeForViewStateChanges(vararg observables: Observable<out VS>) {
    val initialState = getInitialState()
    if (startWithInitialState) {
      subscribeViewState(
          mergeStates(*observables).scan(
              initialState,
              this::viewStateReducer
          ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()),
          BaseView<FVS>::render
      )
    } else {
      subscribeViewState(
          mergeStates(*observables).scanWith(
              { getInitialState() },
              this::viewStateReducer
          ).skip(1).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()), BaseView<FVS>::render
      )
    }
  }

  @MainThread
  private fun subscribeViewState(
      viewStateObservable: Observable<FVS>,
      consumer: (V, FVS) -> Unit
  ) {
    if (subscribeViewStateMethodCalled) {
      throw IllegalStateException(
          "subscribeViewState() method is only allowed to be called once"
      )
    }
    subscribeViewStateMethodCalled = true
    this.viewStateConsumer = consumer
    viewStateDisposable = viewStateObservable.subscribeWith(
        DisposableViewStateObserver(viewStateBehaviorSubject)
    )
  }

  @MainThread
  private fun subscribeViewStateConsumerActually(view: V) {
    if (viewStateConsumer == null) {
      throw RuntimeException(
          "viewStateConsumer is null. This is internal bug."
      )
    }
    viewRelayConsumerDisposable =
        viewStateBehaviorSubject.subscribe { vs -> viewStateConsumer?.invoke(view, vs) }
  }

  @MainThread
  protected abstract fun bindIntents()

  @MainThread
  protected abstract fun viewStateReducer(
      previousState: FVS,
      changes: VS
  ): FVS

  protected abstract fun getInitialState(): FVS
  protected open fun unbindIntents() {
  }

  @MainThread
  protected fun <I> intent(binder: (V) -> Observable<I>): Observable<I> {
    val intentRelay = UnicastSubject.create<I>()
    intentRelaysBinders.add(IntentRelayBinderPair(intentRelay, binder))
    return intentRelay
  }

  private fun mergeStates(vararg viewStateObservables: Observable<out VS>): Observable<VS> {
    return Observable.merge(viewStateObservables.asIterable())
  }

  @MainThread
  private fun <I> bindIntentActually(
      view: V,
      relayBinderPair: IntentRelayBinderPair<*>
  ): Observable<I> {
    val intentRelay =
        relayBinderPair.intentRelaySubject as? Subject<I> ?: throw RuntimeException(
            "IntentRelay from binderPair is null. This is internal bug."
        )
    val intentBinder =
        relayBinderPair.intentBinder as? (V) -> Observable<I> ?: throw RuntimeException(
            "Intent Binder is null. This is internal bug."
        )
    val intent = intentBinder.invoke(view)
    if (intentDisposables == null) {
      intentDisposables = CompositeDisposable()
    }
    if (actionDisposable == null) {
      actionDisposable = CompositeDisposable()
    }
    intentDisposables?.add(
        intent.subscribeWith(
            DisposableIntentObserver(intentRelay)
        )
    )
    return intentRelay
  }

  inline fun <T, R> Observable<T>.switchMapToViewState(
      crossinline source: (T) -> Observable<R>,
      crossinline mapSuccess: (R) -> VS,
      crossinline mapError: (Throwable, T) -> VS): Observable<VS> {
    return this.switchMap { value ->
      Observable.just(value)
          .flatMap {
            return@flatMap Observable.just(value)
                .flatMap {
                  source.invoke(it)
                }
                .map { mapSuccess.invoke(it) }

                .onErrorReturn {
                  mapError.invoke(it, value)
                }
          }
    }
  }

  inline fun <T, R> Observable<T>.switchMapToViewState(
      crossinline source: (T) -> Observable<R>,
      crossinline mapSuccess: (R) -> VS,
      crossinline mapError: (Throwable, T) -> VS,
      crossinline mapLoading: ((T) -> VS)
  ): Observable<VS> {
    return this.switchMap { value ->
      Observable.just(value)
          .flatMap { it ->
            return@flatMap Observable.just(it)
                .flatMap {
                  source.invoke(it)
                }
                .map { mapSuccess.invoke(it) }

                .onErrorReturn {
                  mapError.invoke(it, value)
                }
                .startWith(mapLoading.invoke(it))
          }
    }
  }

  protected fun <T, R, VS> Observable<T>.flatMapToViewState(
      source: (T) -> Observable<R>,
      mapSuccess: (R) -> VS,
      mapError: (Throwable) -> VS,
      mapLoading: ((T) -> VS)? = null
  ): Observable<VS> {
    return this.flatMap { value ->
      Observable.just(value)
          .flatMap { it ->
            var observable = Observable.just(it)
                .flatMap {
                  source.invoke(it)
                }
                .map { mapSuccess.invoke(it) }
                .onErrorReturn { mapError.invoke(it) }
            if (mapLoading != null) {
              observable = observable.startWith(mapLoading.invoke(it))
            }
            observable
          }
    }
  }

  protected inline fun <reified T> Observable<VS>.emmitAfter(crossinline state: (T) -> VS): Observable<VS> where T : VS {
    return this.flatMap {
      if (it is T) {
        Observable.fromIterable(listOf(it, state.invoke(it)))
      } else {
        Observable.just(it)
      }
    }
  }

  protected inline fun <reified T> Observable<VS>.executeActionOn(crossinline action: (T) -> Unit): Observable<VS> where T : VS {
    return this.flatMap {
      if (it is T) {
        actionDisposable?.add(Observable.just(it)
                                  .observeOn(AndroidSchedulers.mainThread())
                                  .subscribe({ action.invoke(it) }, {}))
      }
      Observable.just(it)
    }
  }

}
