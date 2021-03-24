package com.suggito.rentalApp.ui.rental_detail

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.suggito.rentalApp.Items
import com.suggito.rentalApp.Rentals
import java.util.*

class RentalDetailModel {
    companion object {
        const val ITEM = "Item"
        const val RENTAL = "rentals"
    }
    private val firestore: FirebaseFirestore = Firebase.firestore
    private val storage = Firebase.storage

    fun getItemImage(fileName: String): StorageReference {
        val imageRef = storage.reference.child("item_icon").child(fileName)
        return imageRef
    }

    fun rentalDateCheck(id: String): CollectionReference {
        return firestore.collection(ITEM).document(id).collection(RENTAL)
    }

    fun addRentalData(itemId: String, rental: Rentals): Task<DocumentReference> {
        return firestore.collection(ITEM).document(itemId).collection(RENTAL).add(rental)
    }

    fun getDB(): FirebaseFirestore {
        return firestore
    }

    fun getItemDocument(itemId: String): DocumentReference {
        return firestore.collection(ITEM).document(itemId)
    }
}