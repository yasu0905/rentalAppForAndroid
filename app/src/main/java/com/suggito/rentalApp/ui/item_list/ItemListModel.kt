package com.suggito.rentalApp.ui.item_list

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class ItemListModel {
    companion object {
        const val ITEM = "Item"
    }
    private val storage = Firebase.storage

    fun getItemImage(fileName: String): StorageReference {
        val imageRef = storage.reference.child("item_icon").child(fileName)
        return imageRef
    }
}