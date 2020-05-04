package com.menilv.reddit.network.repository

import com.menilv.reddit.model.response.Children
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeDataRepository @Inject constructor() : DataRepository<Any?, List<Children>>() {
    override fun fetchData(payload: Any?): Observable<List<Children>> {
        return retrofitClient
            .getRedditAPI()
            .getListItems()
            .subscribeOn(Schedulers.io())
            .map { it.data.children!!.sortedByDescending { x -> x.data!!.ups } }
            .toObservable()
        // the map not only takes child objects (posts) but also includes sorting them by
        // upvote count in descending order
    }
}