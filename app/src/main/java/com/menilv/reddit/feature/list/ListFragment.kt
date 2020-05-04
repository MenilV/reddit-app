package com.menilv.reddit.feature.list

import android.view.View
import com.menilv.reddit.R
import com.menilv.reddit.common.BaseFragment
import com.menilv.reddit.feature.list.adapter.PostsAdapter
import com.menilv.reddit.model.response.Children
import com.menilv.reddit.model.response.DataX
import florent37.github.com.rxlifecycle.RxLifecycle
import io.reactivex.Observable
import javax.inject.Inject
import kotlinx.android.synthetic.main.list_fragment.*


class ListFragment : BaseFragment(R.layout.list_fragment), ListView {

    @Inject
    override lateinit var presenter: ListPresenter

    @Inject
    lateinit var adapter: PostsAdapter

    override fun initDependencies() {
        mainComponent.inject(this)
    }

    override fun initUI() {
        super.initUI()
        rv_items.adapter = adapter
    }

    override fun render(state: ListFullViewState) {
        showError(state.error)
        showData(state.posts)
        showLoader(state.loading)
    }

    private fun showData(posts: List<Children>?){
        if(posts.isNullOrEmpty()) return
        rv_items.visibility = View.VISIBLE
        adapter.items = posts.map { it.data!! }
    }

    override fun onDataLoad(): Observable<Unit> = RxLifecycle.onResume(this).map{ Unit }

    override fun onPostPressed(): Observable<DataX> = adapter.onPostPressed


}
