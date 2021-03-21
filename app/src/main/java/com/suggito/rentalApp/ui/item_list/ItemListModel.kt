package com.suggito.rentalApp.ui.item_list

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class ItemListModel {
    companion object {
        const val ITEM = "Item"
    }
    private val storage = Firebase.storage

    fun getItemImage(fileName: String): StorageReference {
        val imageRef = storage.reference.child("item_icon").child("スイカ.png")
        return imageRef
    }

    fun getItemImage2(fileName: String): Task<Uri> {
        val imageRef = storage.reference.child("item_icon").child("スイカ.png")
        return imageRef.downloadUrl
    }

}