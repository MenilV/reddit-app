package com.menilv.reddit.feature.list

import com.menilv.reddit.model.response.Children

sealed class ListViewState

data class ListFullViewState(
    val error: Throwable? = null,
    val loading: Boolean? = null,
    val posts: List<Children>? = null,
    val url: String? = null
)

class ListSuccessViewState(val posts: List<Children>) : ListViewState()
class ListLoadingViewState(val loading: Boolean?) : ListViewState()
class ListErrorViewState(val error: Throwable?) : ListViewState()
class ListUrlViewState(val url: String) : ListViewState()
