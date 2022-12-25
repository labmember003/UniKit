package com.falcon.unikit.network
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class FirstYearApiResponse (
    //val subjects: List<Subject>
    @Json(name = "subjects") val subjects: List<Subject>
): Serializable

