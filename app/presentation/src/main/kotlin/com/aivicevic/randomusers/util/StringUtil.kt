package com.aivicevic.randomusers.util

import java.util.Locale

class StringUtil {

    companion object {

        fun capitalizeWords(string: String): String {
            val words = string.split(" ")
            return StringBuilder().apply {
                for (word in words) {
                    val capWord = word.replaceFirstChar {
                        if (it.isLowerCase()) {
                            it.titlecase(Locale.getDefault())
                        } else {
                            it.toString()
                        }
                    }
                    append("$capWord ")
                }
            }.toString().trim()
        }
    }
}
