package com.menilv.reddit.di.application
import android.content.Context
import android.content.SharedPreferences
import com.menilv.reddit.di.ApiUrl
import com.menilv.reddit.network.HttpClient
import com.menilv.reddit.network.HttpClientImpl
import com.menilv.reddit.network.Interceptor
import com.menilv.reddit.network.InterceptorImpl
import com.menilv.reddit.network.repository.RetrofitClientImpl
import com.menilv.reddit.network.Serializer
import com.menilv.reddit.network.SerializerImpl
import com.menilv.reddit.network.repository.RetrofitClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class MainApplicationModule(
    private val context: Context,
    private val apiUrl: String,
    private val sharedPreferences: SharedPreferences
) {

  @Singleton
  @Provides
  fun appContext(): Context = context

  @Singleton
  @Provides
  fun httpClient(httpClient: HttpClientImpl): HttpClient = httpClient

  @Singleton
  @Provides
  fun interceptor(interceptor: InterceptorImpl): Interceptor = interceptor

  @Singleton
  @Provides
  fun retrofit(retrofit: RetrofitClientImpl): RetrofitClient = retrofit

  @Singleton
  @Provides
  fun serializer(serializer: SerializerImpl): Serializer = serializer

  @Singleton
  @Provides
  @ApiUrl
  fun apiUrl(): String = apiUrl

  @Singleton
  @Provides
  fun sharedPreferences(): SharedPreferences = sharedPreferences

}
