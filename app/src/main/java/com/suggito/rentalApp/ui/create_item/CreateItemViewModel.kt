package com.suggito.rentalApp.ui.create_item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suggito.rentalApp.Items

class CreateItemViewModel : ViewModel() {

    val TAG = "CreateItemViewModel"
    private var model = CreateItemModel()
    private var createResult: MutableLiveData<Boolean> = MutableLiveData()

    fun createItemData(item: Items): LiveData<Boolean> {
        model.addItem(item).addOnSuccessListener {
            createResult.value = true
        }
        .addOnFailureListener { exception ->
            Log.e(TAG, exception.localizedMessage)
            createResult.value = false
        }
        return createResult
    }
}