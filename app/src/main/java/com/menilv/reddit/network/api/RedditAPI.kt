package com.menilv.reddit.network.api

import com.menilv.reddit.model.base.SuccessResponse
import com.menilv.reddit.model.response.Data
import io.reactivex.Single
import retrofit2.http.GET

interface RedditAPI {

    @GET("/.json?raw_json=1")
    fun getListItems(): Single<SuccessResponse<Data>>
}
