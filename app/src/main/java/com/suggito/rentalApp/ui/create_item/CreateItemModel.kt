package com.suggito.rentalApp.ui.create_item

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.suggito.rentalApp.Items
import java.util.*

class CreateItemModel {
    companion object {
        const val ITEM = "Item"
    }

    private val firestore: FirebaseFirestore = Firebase.firestore
    private val storage = Firebase.storage

    fun uploadItemImage(uri: Uri): Task<UploadTask.TaskSnapshot>{
        val ref = storage.reference
        ref.child("item_icon/${uri.lastPathSegment}")
        return ref.putFile(uri)
    }

    fun uploadItemImage(data: ByteArray, fileName: String): Task<UploadTask.TaskSnapshot>{
        val ref = storage.reference
        return ref.child("item_icon/${fileName}").putBytes(data)
    }

    fun addItem(item: Items): Task<DocumentReference> {
        return firestore.collection(ITEM).add(item)
    }
}