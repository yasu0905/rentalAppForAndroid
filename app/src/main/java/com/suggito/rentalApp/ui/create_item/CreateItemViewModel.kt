package com.suggito.rentalApp.ui.create_item

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suggito.rentalApp.Items
import java.io.ByteArrayOutputStream
import java.util.*

class CreateItemViewModel : ViewModel() {

    val TAG = "CreateItemViewModel"
    private var model = CreateItemModel()
    private var createResult: MutableLiveData<Boolean> = MutableLiveData()
    private var uploadResult: MutableLiveData<String> = MutableLiveData()

    fun uploadItemImage(imageView: ImageView): LiveData<String> {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val fileName = "${UUID.randomUUID()}.png"

        model.uploadItemImage(data, fileName).addOnSuccessListener {
            println("success")
            uploadResult.value = fileName
        }
        .addOnFailureListener { exception ->
            Log.e(TAG, exception.localizedMessage)
            uploadResult.value = "failed"
        }
        return uploadResult
    }


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