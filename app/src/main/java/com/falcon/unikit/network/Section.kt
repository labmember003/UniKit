package com.falcon.unikit.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Section (
    @Json(name = "title") val title: String,
    @Json(name = "icon_url") val iconSrc: String = "",
    @Json(name = "contents") val contents: List<Content>
): Serializable

