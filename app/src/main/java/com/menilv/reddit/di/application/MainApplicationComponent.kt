package com.menilv.reddit.di.application
import android.content.Context
import com.menilv.reddit.MainApplication
import com.menilv.reddit.di.ApiUrl
import com.menilv.reddit.network.HttpClient
import com.menilv.reddit.network.Interceptor
import com.menilv.reddit.network.Serializer
import com.menilv.reddit.network.repository.RetrofitClient

interface MainApplicationComponent {
  fun context(): Context
  fun httpClient(): HttpClient
  fun interceptor(): Interceptor
  fun retrofitClient(): RetrofitClient
  fun serializer(): Serializer
  @ApiUrl
  fun apiUrl(): String
  fun inject(app: MainApplication)
}
