package com.falcon.unikit.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Content (
    @Json(name = "source_url") val sourceUrl: String,
    @Json(name = "name") val name: String
): Serializable
