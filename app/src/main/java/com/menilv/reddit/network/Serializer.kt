package com.menilv.reddit.network
import com.squareup.moshi.Moshi

interface Serializer {
  fun getMoshi(): Moshi
}
