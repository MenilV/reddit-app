package com.menilv.reddit.common

interface BaseView<S> {
    fun render(state: S)
}
