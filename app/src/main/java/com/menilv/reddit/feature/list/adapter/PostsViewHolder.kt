package com.menilv.reddit.feature.list.adapter

import android.text.format.DateFormat
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import com.menilv.reddit.R
import com.menilv.reddit.adapter.viewholder.BaseViewHolder
import com.menilv.reddit.model.response.DataX
import kotlinx.android.synthetic.main.post_item.view.*
import java.util.*


class PostsViewHolder(
    parent: ViewGroup,
    adapterView: PostsAdapterView
) : BaseViewHolder<DataX, PostsAdapterView>(parent, R.layout.post_item, adapterView) {

    init {
        itemView.clicks()
            .map { data }
            .subscribe(adapterView.onPostPressed)
    }

    override fun bind(data: DataX) {
        super.bind(data)
        itemView.author.text = String.format(
            itemView.context.resources.getString(R.string.username_template),
            data.author
        )
        itemView.self_text.text = data.selftext
        itemView.title.text = data.title
        // This is the front page of Reddit and posts are sorted by upvotes descending
        // meaning the upvote count is in range of thousands and as such
        // an appropriate approach to showing this number is by diving them by 1000
        // and showing an abbreviated number of upvotes
        itemView.ups.text = String.format("%dk", data.ups / 1000)
        itemView.subreddit.text = String.format(
            itemView.context.resources.getString(R.string.subreddit_template),
            data.subreddit
        )
        if (data.preview != null && data.preview.images!!.isNotEmpty())
        // Since we only need to use the smallest photo (if it exists)
        // Glide loads the first picture sorted by width
        // We can safely assume sorting by width is good enough in this case
        // since all pictures are proportional, meaning sorting by either width
        // or height is correct
            Glide.with(itemView.preview)
                .load(data.preview.images[0].resolutions!!.sortedBy { it.width }[0].url)
                .centerCrop()
                .into(itemView.preview)

        itemView.time.text = getDate(data.createdUtc)
    }

    private fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        return DateFormat.format("MMM dd, hh:mm", calendar).toString()
    }

}