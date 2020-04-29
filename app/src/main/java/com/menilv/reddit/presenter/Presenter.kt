package com.menilv.reddit.presenter

import androidx.annotation.UiThread
import com.menilv.reddit.common.BaseView

interface Presenter {
    @UiThread
    fun attachView(view: BaseView<*>)

    @UiThread
    fun detachView()

    @UiThread
    fun destroy()
}
