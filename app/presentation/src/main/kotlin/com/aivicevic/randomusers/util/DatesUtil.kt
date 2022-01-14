package com.aivicevic.randomusers.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DatesUtil {

    const val ISO_SERVER_DATE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val BASIC_DATE_FORMAT_LONG = "MM/dd/yyyy"

    fun parseDate(date: String, format: String, timeZone: String? = null): Date {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return timeZone?.let {
            formatter.timeZone = TimeZone.getTimeZone(it)
            Calendar.getInstance().apply {
                this.time = formatter.parse(date)
                this.timeZone = TimeZone.getTimeZone(Calendar.getInstance().timeZone.id)
            }.time
        } ?: formatter.parse(date)
    }

    @JvmStatic
    @JvmOverloads
    fun formatDate(date: Date, format: String, timeZone: String? = null): String {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        timeZone?.let {
            formatter.timeZone = TimeZone.getTimeZone(it)
        }
        return formatter.format(date)
    }

    /**
     * Parse date with current format and format it to new format
     *
     * @param date date you want to parse and format
     * @param currentFormat current date format
     * @param currentTimeZone current date time zone
     * @param newFormat new date format
     *
     * @return string of new formatted date
     */
    fun parseAndFormatDate(
        date: String,
        currentFormat: String,
        newFormat: String,
        currentTimeZone: String? = null
    ): String {
        return formatDate(parseDate(date, currentFormat, currentTimeZone), newFormat)
    }
}
