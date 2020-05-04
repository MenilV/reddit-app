package com.menilv.reddit.model.response

import android.os.Parcelable
import com.menilv.reddit.model.base.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "after")
    val after: String?,
    @Json(name = "before")
    val before: Any?,
    @Json(name = "children")
    val children: List<Children>?,
    @Json(name = "dist")
    val dist: Int?,
    @Json(name = "modhash")
    val modhash: String?
)

@Parcelize
@JsonClass(generateAdapter = true)
data class Children(
    @Json(name = "data")
    val `data`: DataX?,
    @Json(name = "kind")
    val kind: String?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class DataX(
    @Json(name = "author")
    val author: String?,
    @Json(name = "created_utc")
    val createdUtc: Long,
    @Json(name = "id")
    val id: String?,
    @Json(name = "preview")
    val preview: Preview?,
    @Json(name = "selftext")
    val selftext: String?,
    @Json(name = "subreddit")
    val subreddit: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "ups")
    val ups: Int,
    @Json(name = "permalink")
    val url: String
) : Parcelable, Entity<String> {
    override fun id(): String = id!!
}

@Parcelize
@JsonClass(generateAdapter = true)
data class Preview(
    @Json(name = "enabled")
    val enabled: Boolean,
    @Json(name = "images")
    val images: List<Image>?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "id")
    val id: String,
    @Json(name = "resolutions")
    val resolutions: List<Resolution>?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Resolution(
    @Json(name = "height")
    val height: Int,
    @Json(name = "url")
    val url: String,
    @Json(name = "width")
    val width: Int
) : Parcelable
