package com.suggito.rentalApp.ui.create_item

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.suggito.rentalApp.Items
import com.suggito.rentalApp.R
import kotlinx.android.synthetic.main.fragment_create_item.*

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
        createItemViewModel =
            ViewModelProvider(this).get(CreateItemViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_create_item, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        createItemViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
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


            val item = Items(
                "",
                itemNameEditText.text.toString(),
                itemTypeEditText.text.toString(),
                ""
            )
            createItemViewModel.createItemData(item).observe(viewLifecycleOwner, Observer {
                val result = it
                if (result){
                    Toast.makeText(this.context, "登録しました。", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.nav_home)
                } else {
                    Toast.makeText(this.context, "登録に失敗しました。", Toast.LENGTH_LONG).show()
                }

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
}