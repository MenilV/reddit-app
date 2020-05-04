package com.menilv.reddit.adapter

import com.menilv.reddit.adapter.viewholder.BaseViewHolder
import com.menilv.reddit.model.base.Entity

abstract class BaseAdapter<T : Entity<*>, VH : BaseViewHolder<T, *>> : androidx.recyclerview.widget.RecyclerView.Adapter<VH>() {
    var items: List<T> by AdapterItemsDelegate()

    override fun onBindViewHolder(
        holder: VH,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}