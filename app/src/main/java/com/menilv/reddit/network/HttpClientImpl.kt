package com.menilv.reddit.network
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HttpClientImpl @Inject constructor(private val interceptor: Interceptor) : HttpClient {
  override fun getClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)

    interceptor.getApplicationInterceptors()
        .forEach {
          okHttpClientBuilder.addInterceptor(it)
        }
    return okHttpClientBuilder.build()
  }

  override fun getUploadClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(90, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)


    okHttpClientBuilder.addInterceptor(interceptor.loggingInterceptor())
    return okHttpClientBuilder.build()
  }
}
