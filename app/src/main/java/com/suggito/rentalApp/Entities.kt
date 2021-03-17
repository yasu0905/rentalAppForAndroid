package com.suggito.rentalApp

import com.google.firebase.firestore.DocumentId
import java.io.Serializable
import java.util.*

data class LoginUser(
   val authority: String = "",
   val email: String = "",
   val password: String = "",
   val userName: String = "",
   val createBy: Date = Date()
): Serializable

data class Items(
   @DocumentId val id: String = "",
   val name: String = "",
   val type: String = "",
   val url: String = "",
   val search_channel: List<String> = listOf(),
   val search_userName: List<String> = listOf()
): Serializable