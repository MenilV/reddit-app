package com.menilv.reddit.feature.list.adapter

import android.view.ViewGroup
import com.menilv.reddit.adapter.BaseAdapter
import com.menilv.reddit.model.response.DataX
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PostsAdapter @Inject constructor() : BaseAdapter<DataX, PostsViewHolder>(), PostsAdapterView {
    override val onPostPressed: PublishSubject<DataX> = PublishSubject.create<DataX>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder =
        PostsViewHolder(parent, this)

}