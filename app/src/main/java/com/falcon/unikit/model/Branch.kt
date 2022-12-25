package com.falcon.unikit.model

enum class Branch(
    val displayName: String
) {
    AIDS("AIDS"),
    AIML("AIML"),
    IIOT("IIOT"),
    AR("AR");

    companion object {
        fun getBranch(branchName: String): Branch {
            return Branch.values().find { it.displayName == branchName }!!
        }
    }
}