package com.menilv.reddit.network
import okhttp3.OkHttpClient

interface HttpClient {
  fun getClient(): OkHttpClient
  fun getUploadClient(): OkHttpClient
}
