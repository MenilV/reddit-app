package com.menilv.reddit.feature.list

import com.menilv.reddit.common.BaseView
import com.menilv.reddit.model.response.DataX
import io.reactivex.Observable

interface ListView : BaseView<ListFullViewState> {
    fun onDataLoad(): Observable<Unit>
    fun onPostPressed(): Observable<DataX>
}
