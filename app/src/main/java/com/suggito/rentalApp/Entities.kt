package com.suggito.rentalApp

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

data class LoginUser(
   @DocumentId val id: String = "",
   val authority: String = "",
   val email: String = "",
   val password: String = "",
   val userName: String = "",
   val createBy: Date = Date()
): Serializable

@Parcelize
data class Items(
   @DocumentId val id: String = "",
   val name: String = "",
   val type: String = "",
   val url: String = "",
   val search_channel: List<String> = listOf(),
   val search_userName: List<String> = listOf()
): Parcelable