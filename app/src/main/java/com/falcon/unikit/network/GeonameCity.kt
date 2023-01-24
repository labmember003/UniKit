package com.falcon.unikit.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
class GeonameCity (
    @Json(name = "name") val cityName: Int
): Serializable
