package com.menilv.reddit.network
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.EnumJsonAdapter
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.menilv.reddit.model.base.ErrorMessageEnum
import java.util.Date
import javax.inject.Inject

class SerializerImpl @Inject constructor() : Serializer {
  override fun getMoshi(): Moshi {
    return Moshi.Builder()
        .add(
            ErrorMessageEnum::class.java,
            EnumJsonAdapter.create(ErrorMessageEnum::class.java).withUnknownFallback(ErrorMessageEnum.SOMETHING_WENT_WRONG))
        .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .add(KotlinJsonAdapterFactory())
        .build()
  }
}
