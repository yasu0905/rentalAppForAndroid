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
    private var addResult: MutableLiveData<Boolean> = MutableLiveData()
    private var returnResult: MutableLiveData<Boolean> = MutableLiveData()
    private var rentals: MutableLiveData<List<Rentals>?> = MutableLiveData()

    fun getItemImage(fileName: String): StorageReference {
        return model.getItemImage(fileName)
    }

    fun rentalDateCheck(id: String, searchRentalDate: Date, searchReturnDate: Date): Task<QuerySnapshot> {
        return model.rentalDateCheck(id).get()
    }

    fun reloadRentalData(itemId: String): LiveData<List<Rentals>?> {
        model.getRentalDocuments(itemId).addOnSuccessListener { snapshot ->
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
            rentals.value = null
        }
        return rentals
    }

    fun addRentalData(item: Items, rental: Rentals): LiveData<Boolean> {
        model.addRentalData(item, rental).addOnSuccessListener {
            println("Success")
            addResult.value = true
        }
        .addOnFailureListener { exception ->
            Log.e(TAG, exception.localizedMessage)
            addResult.value = false
        }
        return addResult
    }

    fun returnRentalData(item: Items, rental: Rentals): LiveData<Boolean> {
        model.returnRentalData(item, rental).addOnSuccessListener {
            println("Success")
            returnResult.value = true
        }
        .addOnFailureListener { exception ->
            Log.e(TAG, exception.localizedMessage)
            returnResult.value = false
        }
        return returnResult
    }
}