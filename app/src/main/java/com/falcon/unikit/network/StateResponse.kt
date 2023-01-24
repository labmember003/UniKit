package com.falcon.unikit.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
class StateResponse (
    @Json(name = "name") val cityName: List<GeonameCity>
): Serializable
