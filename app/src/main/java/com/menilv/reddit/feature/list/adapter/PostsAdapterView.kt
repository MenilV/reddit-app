package com.menilv.reddit.feature.list.adapter

import com.menilv.reddit.model.response.DataX
import io.reactivex.subjects.PublishSubject

interface PostsAdapterView {
    val onPostPressed: PublishSubject<DataX>
}