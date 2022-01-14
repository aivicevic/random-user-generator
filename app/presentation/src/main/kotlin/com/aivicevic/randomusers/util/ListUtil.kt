package com.aivicevic.randomusers.util

object ListUtil {

    fun <T> concatenate(vararg lists: List<T>): List<T> {
        return listOf(*lists).flatten()
    }
}
