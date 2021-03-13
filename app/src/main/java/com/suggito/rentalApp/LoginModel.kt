package com.suggito.rentalApp

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginModel {
    private val auth: FirebaseAuth = Firebase.auth

    fun userLogin(loginUser: LoginUser): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(loginUser.email, loginUser.password)
    }
}