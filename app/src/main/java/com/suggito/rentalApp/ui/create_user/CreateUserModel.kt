package com.suggito.rentalApp.ui.create_user

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.suggito.rentalApp.LoginUser

class CreateUserModel {
    companion object {
        const val USER = "User"
    }

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = Firebase.firestore

    fun createAuthUser(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun createUserData(user: LoginUser): Task<DocumentReference> {
        return firestore.collection(USER).add(user)
    }
}