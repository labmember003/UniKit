package com.falcon.unikit.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
class GeonameCountry (
    @Json(name = "geonameId") val geonameId: Int,
    @Json(name = "countryName") val countryName: Int,
): Serializable
