package com.menilv.reddit.common

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import com.google.android.material.snackbar.Snackbar
import com.menilv.reddit.MainActivity
import com.menilv.reddit.R
import com.menilv.reddit.di.fragment.DaggerFragmentComponent
import com.menilv.reddit.di.fragment.FragmentComponent
import com.menilv.reddit.di.fragment.FragmentModule
import com.menilv.reddit.extension.getErrorMessageStringRes
import com.menilv.reddit.extension.toErrorMessage
import com.menilv.reddit.presenter.Presenter
import com.nihad92.delegatebinder.bundlebinding.BundleBinder
import florent37.github.com.rxlifecycle.RxLifecycle
import io.reactivex.Observable

abstract class BaseFragment(@get:LayoutRes val layoutId: Int) : Fragment() {
    abstract val presenter: Presenter?
    private var snackbar: Snackbar? = null
    private val requestId: Int by BundleBinder("request_id", -1)

    private var resultData: Any? = null


    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        initDependencies()
    }

    abstract fun initDependencies()

    open fun initUI() {

    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 750
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 750
        }
        return inflater.inflate(layoutId, container, false)
    }

    override fun onCreateAnimation(
        transit: Int,
        enter: Boolean,
        nextAnim: Int
    ): Animation? {
        var animation = super.onCreateAnimation(transit, enter, nextAnim)

        if (animation == null && nextAnim != 0) {
            animation = AnimationUtils.loadAnimation(activity, nextAnim)
        }

        if (animation != null) {
            view?.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            animation.setAnimationListener(FragmentTransitionAnimationListener())
        }

        return animation
    }

    final override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        showLoader(false)
        initUI()
    }

    final override fun onStart() {
        super.onStart()
    }

    final override fun onResume() {
        super.onResume()
        presenter?.attachView(this as BaseView<*>)
    }

    override fun onPause() {
        super.onPause()
        presenter?.detachView()
    }

    final override fun onStop() {
        super.onStop()
    }

    final override fun onDestroy() {
        super.onDestroy()
        if (activity?.isChangingConfigurations == false) {
            presenter?.destroy()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        snackbar?.dismiss()
        hideKeyboard(requireActivity())
    }

    protected fun showSuccess(message: String?) {
        if (message == null && snackbar?.isShown == true) {
            snackbar?.dismiss()
            return
        }

        if (message == null) {
            return
        }

        snackbar = view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG) }
        snackbar?.show()
    }

    protected fun showError(messageKey: Throwable?) {
        if (messageKey == null) return
        val message = resources.getString(messageKey.toErrorMessage().getErrorMessageStringRes())

        snackbar = view?.let { Snackbar.make(it, message, Snackbar.LENGTH_LONG) }
        snackbar?.show()
    }

    protected fun showLoader(show: Boolean?) {
        if (show != null && show) {
            (activity as MainActivity).showLoader()
        } else {
            (activity as MainActivity).hideLoader()
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private inner class FragmentTransitionAnimationListener : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {
        }

        override fun onAnimationEnd(p0: Animation?) {
        }

        override fun onAnimationStart(p0: Animation?) {
            view?.setLayerType(View.LAYER_TYPE_NONE, null)
        }
    }

    val mainComponent: FragmentComponent
        get() {
            return DaggerFragmentComponent.builder()
                .activityComponent((activity as MainActivity).activityComponent)
                .fragmentModule(FragmentModule(this))
                .build()
        }
}
