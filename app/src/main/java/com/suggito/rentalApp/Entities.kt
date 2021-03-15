package com.suggito.rentalApp

import java.io.Serializable
import java.util.*

data class LoginUser(
   val authority: String = "",
   val email: String = "",
   val password: String = "",
   val userName: String = "",
   val createBy: Date = Date()
): Serializable