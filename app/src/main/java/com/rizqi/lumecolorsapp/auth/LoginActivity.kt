package com.rizqi.lumecolorsapp.auth

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.api.GetDataService
import com.rizqi.lumecolorsapp.api.RetrofitClients
import com.rizqi.lumecolorsapp.main.MenuActivity
import com.rizqi.lumecolorsapp.response.ResponseLogin
import com.rizqi.lumecolorsapp.utils.Constants
import com.rizqi.lumecolorsapp.utils.Constants.LOADING_MSG
import com.rizqi.lumecolorsapp.utils.Constants.LOGGED_IN
import com.rizqi.lumecolorsapp.utils.Constants.LOGGED_STATE
import com.rizqi.lumecolorsapp.utils.Constants.PERIODE
import com.rizqi.lumecolorsapp.utils.Constants.SP_LEVEL
import com.rizqi.lumecolorsapp.utils.SharedPreferencesUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferencesUtils
    private lateinit var datePicker: DatePickerDialog
    private lateinit var mLoading: ProgressDialog
//    Variable From Layout
    private lateinit var login: LinearLayout
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var periode: LinearLayout
    private lateinit var mTxtPeriode: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mLoading = ProgressDialog(this@LoginActivity)
        mLoading.setCancelable(false)

//        Variable From Layout
        login = findViewById(R.id.button_login)
        username = findViewById(R.id.edit_username)
        password = findViewById(R.id.edit_password)
        periode = findViewById(R.id.periode)
        mTxtPeriode = findViewById(R.id.txt_periode)

        sharedPreferences = SharedPreferencesUtils(this@LoginActivity)

        if (sharedPreferences.getStringSharedPreferences(LOGGED_STATE) == LOGGED_IN){
            startActivity(Intent(this@LoginActivity, MenuActivity::class.java))
            finish()
            return
        }

        setPeriode()

        login.setOnClickListener {
            if(validation()) {
                responseLogin(username.text.toString().trim(), password.text.toString().trim(), mTxtPeriode.text.toString().trim())
            }
        }

    }

    private fun validation(): Boolean {
        if(username.text.isEmpty()) {
            username.error = "Username belum diisi."
            username.requestFocus()
            return false
        }
        if(password.text.isEmpty()) {
            password.error = "password belum diisi."
            password.requestFocus()
            return false
        }
        return true
    }

    private fun setPeriode() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        mTxtPeriode.text = "${month + 1}/${year}"

        periode.setOnClickListener {
            datePicker = DatePickerDialog(this@LoginActivity,
                { view, year, month, dayOfMonth ->
                    mTxtPeriode.text = "${month + 1}/${year}"
                }, year, month, day)
            datePicker.show()
        }

    }

    private fun responseLogin(username: String, password: String, periode: String) {
        mLoading.setMessage(LOADING_MSG)
        mLoading.show()

        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.userLogin(username, password, periode)

        call.enqueue(object : Callback<ResponseLogin> {

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {

                Toast.makeText(
                    this@LoginActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("FAILED :", t.message.toString())
                mLoading.dismiss()

            }

            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {

                val res = response.body()!!

                if (res.status == Constants.STAT200) {

                    val data = res.data[0]

                    setDataUser(SP_LEVEL, 0, data.nama)
                    setDataUser(PERIODE, 0, data.periode)
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

                mLoading.dismiss()

            }

        })
    }

    fun setDataUser(key: String, int: Int, string: String){
        if (string != ""){
            sharedPreferences.setSharedPreferences(key, string)
        } else {
            sharedPreferences.setSharedPreferences(key, int)
        }
    }

}