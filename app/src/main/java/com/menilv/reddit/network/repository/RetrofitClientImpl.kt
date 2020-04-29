package com.menilv.reddit.network.repository
import com.menilv.reddit.di.ApiUrl
import com.menilv.reddit.network.HttpClient
import com.menilv.reddit.network.RxCallAdapterFactory
import com.menilv.reddit.network.Serializer
import com.menilv.reddit.network.api.RedditAPI
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RetrofitClientImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val serializer: Serializer,
    @ApiUrl private val apiUrl: String
) : RetrofitClient {

  override fun getRedditAPI(): RedditAPI {
    return Retrofit.Builder()
        .baseUrl(apiUrl)
        .addConverterFactory(MoshiConverterFactory.create(serializer.getMoshi()))
        .addCallAdapterFactory(RxCallAdapterFactory.create())
        .client(httpClient.getClient())
        .build()
        .create(RedditAPI::class.java)
  }
}
