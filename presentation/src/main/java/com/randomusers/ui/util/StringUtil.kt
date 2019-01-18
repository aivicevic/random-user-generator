package com.randomusers.ui.util

class StringUtil {

    companion object {
        fun capitalizeWords(string: String): String {
            val words = string.split(" ")
            return StringBuilder().apply {
                for (word in words) {
                    val capWord = word.capitalize()
                    append("$capWord ")
                }
            }.toString().trim()
        }
    }
}
