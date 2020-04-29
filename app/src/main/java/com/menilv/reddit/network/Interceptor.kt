package com.menilv.reddit.network

interface Interceptor {
  fun getApplicationInterceptors(): List<okhttp3.Interceptor>
  fun loggingInterceptor(): okhttp3.Interceptor
}
