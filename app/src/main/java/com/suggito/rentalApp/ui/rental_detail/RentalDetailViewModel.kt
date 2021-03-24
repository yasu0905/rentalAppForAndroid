package com.suggito.rentalApp.ui.rental_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.suggito.rentalApp.Items
import com.suggito.rentalApp.Rentals
import java.util.*

class RentalDetailViewModel : ViewModel() {
    val TAG = "RentalDetailViewModel"
    private var model = RentalDetailModel()
    //private var isEmptyResult: MutableLiveData<Boolean> = MutableLiveData()

    fun getItemImage(fileName: String): StorageReference {
        return model.getItemImage(fileName)
    }

    fun rentalDateCheck(id: String, searchRentalDate: Date, searchReturnDate: Date): Task<QuerySnapshot> {
        return model.rentalDateCheck(id).get()
    }

    fun addRentalData(itemId: String, rental: Rentals) {
        model.addRentalData(itemId, rental).addOnSuccessListener {
            println("Success")
        }
        .addOnFailureListener { exception ->
            Log.e(TAG, exception.localizedMessage)
        }
    }

    fun updateRentalData(item: Items, rental: Rentals) {
        val ref = model.getItemDocument(item.id)
        model.getDB().runTransaction {
            //機材の更新
            item.search_channel.add(rental.channel)
            item.search_userName.add(rental.userName)
            ref.set(item)

            //レンタル情報の更新
            ref.collection("rentals").document(rental.id).set(rental)

        }
    }
}