package com.menilv.reddit.feature.list

import android.content.Intent
import android.net.Uri
import com.menilv.reddit.common.BasePresenter
import com.menilv.reddit.di.fragment.Navigator
import com.menilv.reddit.network.repository.HomeDataRepository
import io.reactivex.Observable
import javax.inject.Inject

class ListPresenter @Inject constructor(private val homeDataRepository: HomeDataRepository,
                                        private val navigator: Navigator) :
    BasePresenter<ListView, ListViewState, ListFullViewState>() {

    override fun getInitialState(): ListFullViewState = ListFullViewState()

    override fun bindIntents() {
        val onDataLoad = intent(ListView::onDataLoad)
            .switchMapToViewState(
                { homeDataRepository.fetch(null) },
                { ListSuccessViewState(it) },
                { throwable, _ -> ListErrorViewState(throwable) },
                { ListLoadingViewState(true) })
            .emmitAfter<ListErrorViewState> { ListErrorViewState(null) }
            .emmitAfter<ListSuccessViewState> { ListLoadingViewState(false) }

        val onPostPressed = intent(ListView::onPostPressed)
            .switchMapToViewState(
                { Observable.just(it.url) },
                { ListUrlViewState(it) },
                { throwable, _ -> ListErrorViewState(throwable) })
            .emmitAfter<ListErrorViewState> { ListErrorViewState(null) }
            .executeActionOn<ListUrlViewState> { navigator.startActivityIntent(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.reddit.com"+it.url))) }


        subscribeForViewStateChanges(onDataLoad, onPostPressed)
    }

    override fun viewStateReducer(
        previousState: ListFullViewState,
        changes: ListViewState
    ): ListFullViewState {
        return when (changes) {
            is ListSuccessViewState -> previousState.copy(error = null, posts = changes.posts)
            is ListLoadingViewState -> previousState.copy(loading = changes.loading)
            is ListErrorViewState   -> previousState.copy(error = changes.error, loading = false)
            is ListUrlViewState     -> previousState.copy(url = changes.url)
        }
    }
}