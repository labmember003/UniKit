package com.falcon.unikit.network
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Merge (
    @Json(name = "source_url") val source_url: String = "",
    @Json(name = "merchName") val merchName: String = "",
    @Json(name = "merchPrice") val merchPrice: String = ""
): Serializable
