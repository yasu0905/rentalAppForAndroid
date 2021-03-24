package com.suggito.rentalApp

import android.content.Context
import android.text.format.DateFormat
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import java.io.InputStream
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

    fun dateToString(date: Date, pattern: String = "yyyy/MM/dd"): CharSequence? {
        return DateFormat.format(pattern, date)
    }
}


