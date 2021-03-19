package com.suggito.rentalApp.ui.create_user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suggito.rentalApp.LoginUser
import com.suggito.rentalApp.ui.create_item.CreateItemModel

class CreateUserViewModel : ViewModel() {

    val TAG = "CreateUserViewModel"
    private var model = CreateUserModel()
    private var createDataResult: MutableLiveData<Boolean> = MutableLiveData()
    //private var createAuthResult: MutableLiveData<Boolean> = MutableLiveData()

    fun createAuthUser(email: String, password: String): LiveData<Boolean> {
        val result: MutableLiveData<Boolean> = MutableLiveData()
        model.createAuthUser(email, password).addOnSuccessListener {
            result.value = true
        }
        .addOnFailureListener { exception ->
            Log.e(TAG, exception.localizedMessage)
            result.value = false
        }
        return result
    }

    fun createUserData(user: LoginUser): LiveData<Boolean> {
        val result: MutableLiveData<Boolean> = MutableLiveData()
        model.createUserData(user).addOnSuccessListener {
            result.value = true
        }
        .addOnFailureListener { exception->
            Log.e(TAG, exception.localizedMessage)
            result.value = false
        }
        return result
    }
}