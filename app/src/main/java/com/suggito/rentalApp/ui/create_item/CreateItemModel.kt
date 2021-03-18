package com.suggito.rentalApp.ui.create_item

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.suggito.rentalApp.Items

class CreateItemModel {
    companion object {
        const val ITEM = "Item"
    }

    private val firestore: FirebaseFirestore = Firebase.firestore

    fun addItem(item: Items): Task<DocumentReference> {
        return firestore.collection(ITEM).add(item)
    }
}