package com.suggito.rentalApp.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.suggito.rentalApp.LoginUser
import com.suggito.rentalApp.MainActivity
import com.suggito.rentalApp.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    //private lateinit var loginUser: LoginUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchBtn.setOnClickListener {
            //キーボード閉じる
            closeKeyboard()

            val searchData = hashMapOf<String, Any?>(
                "name" to nameSearchText.text.toString(),
                "type" to typeSearchText.text.toString(),
                "search_channel" to channelSearchText.text.toString(),
                "search_userName" to userNameSearchText.text.toString()
            )
            //※LiveDataが存在していたらObserverに入る仕様になっている
            homeViewModel.removeLiveData(viewLifecycleOwner)
            homeViewModel.getItems(searchData).observe(viewLifecycleOwner, Observer { items ->
                if (items == null) {
                    return@Observer
                }
                if (items.isEmpty()) {
                    Toast.makeText(this.context, "検索に該当するデータがありませんでした。", Toast.LENGTH_LONG).show()
                    return@Observer
                }
                val action = HomeFragmentDirections.actionNavHomeToNavItemList(items.toTypedArray())
                findNavController().navigate(action)
            })
        }
    }

    private fun closeKeyboard() {
        val v = activity?.currentFocus
        if (v != null) {
            val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}

