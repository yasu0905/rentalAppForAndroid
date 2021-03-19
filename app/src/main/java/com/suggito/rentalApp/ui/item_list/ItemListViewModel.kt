package com.suggito.rentalApp.ui.item_list

import androidx.lifecycle.ViewModel
import com.google.firebase.storage.StorageReference
import com.suggito.rentalApp.ui.create_item.CreateItemModel

class ItemListViewModel : ViewModel() {
    val TAG = "ItemListViewModel"
    private var model = ItemListModel()

    fun getItemImage(fileName: String): StorageReference{
        return model.getItemImage(fileName)
    }
}

