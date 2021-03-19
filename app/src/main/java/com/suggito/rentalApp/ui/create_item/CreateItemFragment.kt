package com.suggito.rentalApp.ui.create_item

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.suggito.rentalApp.Items
import com.suggito.rentalApp.MainActivity
import com.suggito.rentalApp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create_item.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class CreateItemFragment : Fragment() {

    companion object {
        private const val READ_REQUEST_CODE: Int = 200
    }
    private lateinit var createItemViewModel: CreateItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createItemViewModel = ViewModelProvider(this).get(CreateItemViewModel::class.java)
        return inflater.inflate(R.layout.fragment_create_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, READ_REQUEST_CODE)
        }

        createItemBtn.setOnClickListener {
            createItemViewModel.uploadItemImage(itemImage).observe(viewLifecycleOwner, Observer { result ->
                if (result == "failed") {
                    Toast.makeText(this.context, "画像のアップロードに失敗しました。", Toast.LENGTH_LONG).show()
                    return@Observer
                }
                val item = Items(
                    "",
                    itemNameEditText.text.toString(),
                    itemTypeEditText.text.toString(),
                    result
                )
                createItemViewModel.createItemData(item).observe(viewLifecycleOwner, Observer { result ->
                    if (!result){
                        Toast.makeText(this.context, "登録に失敗しました。", Toast.LENGTH_LONG).show()
                        return@Observer

                    }
                    Toast.makeText(this.context, "登録しました。", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.nav_home)
                })
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            READ_REQUEST_CODE -> {
                try {
                    data?.data?.also { uri ->
                        val inputStream = this.requireContext().contentResolver?.openInputStream(uri)
                        val image = BitmapFactory.decodeStream(inputStream)
                        itemImage.setImageBitmap(image)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this.context, "エラーが発生しました", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // Method to save an bitmap to a file
    private fun bitmapToFile(bitmap: Bitmap): Uri {
        // Get the context wrapper
        val wrapper = ContextWrapper(this.context)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.jpg")

        try{
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return Uri.parse(file.absolutePath)
    }
}