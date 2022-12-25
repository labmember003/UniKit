package com.falcon.unikit.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
class SecondYearApiResponse (
    @Json(name = "branches") val branches: List<Branch>
): Serializable
