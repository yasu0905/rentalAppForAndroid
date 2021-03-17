package com.suggito.rentalApp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suggito.rentalApp.Items
import com.suggito.rentalApp.LoginUser

class HomeViewModel : ViewModel() {

    private var model = HomeModel()
    private var items: MutableLiveData<List<Items>> = MutableLiveData()

    fun getItems(searchData: Map<String, Any?>): LiveData<List<Items>> {
        model.searchItem(searchData).addOnSuccessListener { snapshot ->
            if (snapshot.toObjects(Items::class.java) != null) {
                var itemList : MutableList<Items> = mutableListOf()
                for (item in snapshot.toObjects(Items::class.java)) {
                    itemList.add(item)
                }
                items.value = itemList
            }
        }
        return items
    }

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text
}