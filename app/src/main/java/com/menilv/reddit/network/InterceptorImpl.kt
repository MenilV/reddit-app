package com.menilv.reddit.network
import com.orhanobut.logger.Logger
import com.menilv.reddit.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import javax.inject.Inject
import android.util.Log

class InterceptorImpl @Inject constructor() :
  BaseInterceptor(),
  Interceptor {
  override var language = "en"

  override fun loggingInterceptor(): okhttp3.Interceptor {
    val loggingInterceptor = HttpLoggingInterceptor { message ->
      if (BuildConfig.DEBUG) {
        Log.e("API", message)
      } else {
        Logger.json(message)
      }
    }
    loggingInterceptor.level = BODY
    return loggingInterceptor
  }

  override fun getApplicationInterceptors(): List<okhttp3.Interceptor> =
    listOf(requestInterceptor(), responseInterceptor(), loggingInterceptor())
}
