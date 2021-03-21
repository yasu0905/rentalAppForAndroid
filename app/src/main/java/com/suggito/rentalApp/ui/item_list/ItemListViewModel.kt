package com.suggito.rentalApp.ui.item_list

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import com.suggito.rentalApp.ui.create_item.CreateItemModel

class ItemListViewModel : ViewModel() {
    val TAG = "ItemListViewModel"
    private var model = ItemListModel()

    fun getItemImage(fileName: String): StorageReference{
        return model.getItemImage(fileName)
    }

    fun setItemImage(fileName: String, imageView: ImageView, context: Context) {
        model.getItemImage2(fileName).addOnSuccessListener {
            Glide.with(context).load(it).centerInside().into(imageView)
        }
    }
}

