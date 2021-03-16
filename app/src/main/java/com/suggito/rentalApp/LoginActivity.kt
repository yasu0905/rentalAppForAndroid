package com.suggito.rentalApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val model: LoginModel = LoginModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            if (emailText.text.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "EMailが入力されていません。", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordText.text.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "パスワードが入力されていません。", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //val user = LoginUser(emailText.text.toString(), passwordText.text.toString())
            val email = emailText.text.toString()
            val password = passwordText.text.toString()
            model.userLogin(email, password)
            .addOnCompleteListener(this) { result ->
                if (result.isSuccessful) {
                    model.getUser(email, password)
                    .addOnSuccessListener { snapshot ->

                        if (snapshot.toObjects(LoginUser::class.java) != null){
                            Toast.makeText(applicationContext, "ログイン成功！！", Toast.LENGTH_SHORT).show()
                            val user = snapshot.toObjects(LoginUser::class.java)
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("user", user[0])
                            startActivity(intent)
                        }
                    }
                    .addOnFailureListener { err ->
                        Log.e("Error", err.localizedMessage)
                    }
                }else {
                    Toast.makeText(applicationContext, "ログインに失敗しました。EMailかパスワードが間違っています。", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { err ->
                Log.e("Error", err.localizedMessage)
            }
        }
    }

}
