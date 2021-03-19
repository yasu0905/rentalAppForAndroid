package com.suggito.rentalApp.ui.create_user

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.suggito.rentalApp.DateConvert
import com.suggito.rentalApp.LoginUser
import com.suggito.rentalApp.R
import kotlinx.android.synthetic.main.fragment_create_user.*
import java.util.*

class CreateUserFragment : Fragment() {

    private lateinit var createUserViewModel: CreateUserViewModel
    private val dateConvert: DateConvert = DateConvert()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        createUserViewModel = ViewModelProvider(this).get(CreateUserViewModel::class.java)
        return inflater.inflate(R.layout.fragment_create_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createUserBtn.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            createUserViewModel.createAuthUser(email, password).observe(viewLifecycleOwner, Observer { result ->
                if (!result) {
                    Toast.makeText(this.context, "ユーザー登録に失敗しました。", Toast.LENGTH_LONG).show()
                    return@Observer
                }
                val userName = userNameEditText.text.toString()
                val authority = authoritySpn.selectedItem.toString()
                val user = LoginUser(
                    "",
                    authority,
                    email,
                    password,
                    userName,
                    Date()
                )
                createUserViewModel.createUserData(user).observe(viewLifecycleOwner, Observer { result ->
                    if (!result) {
                        Toast.makeText(this.context, "ユーザーデータの登録に失敗しました。", Toast.LENGTH_LONG).show()
                        return@Observer
                    }
                    Toast.makeText(this.context, "登録しました。", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.nav_home)
                })
            })
        }
    }
}