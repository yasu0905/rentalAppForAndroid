package com.suggito.rentalApp

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateConvert {
    fun stringToDate(dateString: String, pattern: String = "yyyy/MM/dd"): Date? {
        val sdFormat = try {

            SimpleDateFormat(pattern)
        } catch (e: IllegalArgumentException) {
            null
        }
        return sdFormat?.let {
            try {
                it.parse(dateString)
            } catch (e: ParseException){
                null
            }
        }
    }
}