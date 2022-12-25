package com.falcon.unikit.model

import java.lang.Exception

enum class Year(
    val displayName: String
) {
    FIRST("First Year"),
    SECOND("Second Year"),
    THIRD("Third Year"),
    FOURTH("Fourth Year");

    companion object {
        fun getYear(year: Int): Year {
            return when(year) {
                1 -> FIRST
                2 -> SECOND
                3 -> THIRD
                4 -> FOURTH
                else -> throw Exception("Invalid integer year")
            }
        }
    }
}