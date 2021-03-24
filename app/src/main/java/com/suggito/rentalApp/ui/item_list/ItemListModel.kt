package com.suggito.rentalApp.ui.item_list

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class ItemListModel {
    companion object {
        const val ITEM = "Item"
        const val RENTAL = "rentals"
    }
    private val firestore: FirebaseFirestore = Firebase.firestore
    private val storage = Firebase.storage

    fun getItemImage(fileName: String): StorageReference {
        var url = fileName
        if (url.isEmpty()) {
            url = "noImage.png"
        }
        val imageRef = storage.reference.child("item_icon").child(url)
        return imageRef
    }

    fun getItemHasRentalData(id: String): Task<QuerySnapshot> {
        return firestore.collection(ITEM).document(id).collection(RENTAL).get()
    }

}