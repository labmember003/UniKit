package com.falcon.unikit.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Subject (
    @Json(name = "sub_name") val subjectName: String,
    @Json(name = "semester") val semester: String,
    @Json(name = "icon_url") val iconUrl: String,
    @Json(name = "sections") val sections: List<Section>
): Serializable


