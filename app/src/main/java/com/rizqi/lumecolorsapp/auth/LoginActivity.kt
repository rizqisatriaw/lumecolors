package com.rizqi.lumecolorsapp.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.api.GetDataService
import com.rizqi.lumecolorsapp.api.RetrofitClients
import com.rizqi.lumecolorsapp.main.MenuActivity
import com.rizqi.lumecolorsapp.response.ResponseLogin
import com.rizqi.lumecolorsapp.utils.Constants
import com.rizqi.lumecolorsapp.utils.Constants.LOGGED_IN
import com.rizqi.lumecolorsapp.utils.Constants.LOGGED_STATE
import com.rizqi.lumecolorsapp.utils.Constants.SP_LEVEL
import com.rizqi.lumecolorsapp.utils.SharedPreferencesUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferencesUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val login = findViewById<LinearLayout>(R.id.button_login)
        val username = findViewById<EditText>(R.id.edit_username)
        val password = findViewById<EditText>(R.id.edit_password)

        sharedPreferences = SharedPreferencesUtils(this@LoginActivity)

        if (sharedPreferences.getStringSharedPreferences(LOGGED_STATE) == LOGGED_IN){
            startActivity(Intent(this@LoginActivity, MenuActivity::class.java))
            finish()
            return
        }

        login.setOnClickListener {
            responseLogin(username.text.toString().trim(), password.text.toString().trim())
        }
    }


    private fun responseLogin(username: String, password: String) {

        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.userLogin(username, password)

        call.enqueue(object : Callback<ResponseLogin> {

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

                Toast.makeText(
                    this@LoginActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("FAILED :", t.message.toString())

            }

            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {

                val res = response.body()!!

                if (res.status == Constants.STAT200) {

                    val data = res.data[0]

                    setDataUser(SP_LEVEL, 0, data.nama)
                    setDataUser(LOGGED_STATE, 0, LOGGED_IN)

                    val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                    this@LoginActivity.startActivity(intent)
                    finish()

                } else {

                    Toast.makeText(
                        this@LoginActivity,
                        res.message,
                        Toast.LENGTH_LONG
                    ).show()

                }

                }

            }
        )
    }

    fun setDataUser(key: String, int: Int, string: String){
        if (string != ""){
            sharedPreferences.setSharedPreferences(key, string)
        } else {
            sharedPreferences.setSharedPreferences(key, int)
        }
    }

}