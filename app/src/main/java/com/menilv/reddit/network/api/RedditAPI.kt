package com.menilv.reddit.network.api

import com.menilv.reddit.model.base.SuccessResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET

interface RedditAPI {
    @GET("/")
    fun refreshToken(@Body payload: Any): Single<SuccessResponse<Any>>
}
