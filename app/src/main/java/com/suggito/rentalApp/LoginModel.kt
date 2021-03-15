package com.suggito.rentalApp

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginModel {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = Firebase.firestore

    fun userLogin(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun getUser(email: String, password: String): Task<QuerySnapshot> {
        val coll = firestore.collection("User")
        val query = createQuery(coll, email, password)
        return query.get()
    }

    private fun createQuery(coll: CollectionReference, email: String, password: String): Query {
        var query = coll.limit(1)
        if (!email.isNullOrEmpty()) {
            query = query.whereEqualTo("email", email)
        }
        if (!password.isNullOrEmpty()) {
            query = query.whereEqualTo("password", password)
        }
        return query
    }
}