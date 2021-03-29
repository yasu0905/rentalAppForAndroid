package com.suggito.rentalApp.ui.home

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeModel {

    companion object {
        const val ITEM = "Item"
    }

    private val firestore: FirebaseFirestore = Firebase.firestore

    fun searchItem(searchData: Map<String, Any?>): Task<QuerySnapshot> {
        val coll = firestore.collection(ITEM)
        val query = createQuery(coll, searchData)
        return query.get()
    }

    fun createQuery(coll: CollectionReference, searchData: Map<String, Any?>): Query {
        var query = coll.limit(50)
        if (!searchData["name"].toString().isNullOrEmpty()) {
            query = query.whereEqualTo("name", searchData["name"].toString())
        }
        if (!searchData["type"].toString().isNullOrEmpty()) {
            query = query.whereEqualTo("type", searchData["type"].toString())
        }
        if (!searchData["search_channel"].toString().isNullOrEmpty() && searchData["search_userName"].toString().isNullOrEmpty()) {
            query = query.whereArrayContains("search_channel", searchData["search_channel"].toString())
        }
        if (!searchData["search_userName"].toString().isNullOrEmpty() && searchData["search_channel"].toString().isNullOrEmpty()) {
            query = query.whereArrayContains("search_userName", searchData["search_userName"].toString())
        }
        return query
    }
}