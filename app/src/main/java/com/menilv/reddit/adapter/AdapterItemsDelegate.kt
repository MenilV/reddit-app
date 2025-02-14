package com.menilv.reddit.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.menilv.reddit.model.base.Entity
import com.menilv.reddit.util.EntityDiffUtilCallback
import kotlin.reflect.KProperty

class AdapterItemsDelegate<T : Entity<*>>(private var value: List<T> = ArrayList()) {
    operator fun getValue(
        thisRef: RecyclerView.Adapter<*>,
        property: KProperty<*>
    ): List<T> {
        return this.value
    }

    operator fun setValue(
        thisRef: RecyclerView.Adapter<*>,
        property: KProperty<*>,
        value: List<T>
    ) {
        val result = DiffUtil.calculateDiff(EntityDiffUtilCallback(this.value, value))
        this.value = value
        result.dispatchUpdatesTo(thisRef)
    }
}