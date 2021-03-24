package com.suggito.rentalApp.ui.item_list

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import com.suggito.rentalApp.Items
import com.suggito.rentalApp.Rentals
import com.suggito.rentalApp.ui.create_item.CreateItemModel

class ItemListViewModel : ViewModel() {
    val TAG = "ItemListViewModel"
    private var model = ItemListModel()
    private var rentals: MutableLiveData<List<Rentals>?> = MutableLiveData()

    fun getItemImage(fileName: String): StorageReference{
        return model.getItemImage(fileName)
    }

    fun getItemHasRentalData(id: String): LiveData<List<Rentals>?> {
        model.getItemHasRentalData(id).addOnSuccessListener { snapshot ->
            if (snapshot.toObjects(Rentals::class.java) != null) {
                var rentalList : MutableList<Rentals> = mutableListOf()
                for (rental in snapshot.toObjects(Rentals::class.java)) {
                    rentalList.add(rental)
                }
                rentals.value = rentalList
                rentals.value = null
            }
        }
        .addOnFailureListener { exception ->
            Log.e(TAG, exception.localizedMessage)
        }
        return rentals
    }
}

