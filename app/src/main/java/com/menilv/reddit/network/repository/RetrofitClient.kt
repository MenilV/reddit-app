package com.menilv.reddit.network.repository
import com.menilv.reddit.network.api.RedditAPI

interface RetrofitClient {
  fun getRedditAPI(): RedditAPI
}
