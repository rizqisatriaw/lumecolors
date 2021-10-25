package com.rizqi.lumecolorsapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.api.GetDataService
import com.rizqi.lumecolorsapp.api.RetrofitClients
import com.rizqi.lumecolorsapp.main.MenuActivity
import com.rizqi.lumecolorsapp.response.ResponseLogin

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login = findViewById<LinearLayout>(R.id.button_login)
        val username = findViewById<EditText>(R.id.edit_username)
        val password = findViewById<EditText>(R.id.edit_password)

            login.setOnClickListener {
                ResponseLogin(username.text.toString().trim(), password.text.toString().trim())
            }

    }


    fun ResponseLogin(username: String, password: String) {
        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.userStar()
        call.enqueue(object : Callback<ResponseLogin> {
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {

                val res = response.body()!!

                Toast.makeText(
                    this@LoginActivity,
                    res.name +"  "+ res.height,
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                this@LoginActivity.startActivity(intent)

                }

            }
        )
    }

}