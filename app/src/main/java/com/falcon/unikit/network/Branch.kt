package com.falcon.unikit.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Branch (
    @Json(name = "branch_Name") val branchName: String,
    @Json(name = "subjects") val subjects: List<Subject>,
): Serializable
