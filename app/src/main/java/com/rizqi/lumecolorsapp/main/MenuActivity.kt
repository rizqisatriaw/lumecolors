package com.rizqi.lumecolorsapp.main

import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.utils.Constants.SP_LEVEL
import com.rizqi.lumecolorsapp.utils.SharedPreferencesUtils

class MenuActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferencesUtils

    private lateinit var mLevel: TextView
    private lateinit var loadingIn: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

//        Define Variable
        sharedPreferences = SharedPreferencesUtils(this@MenuActivity)

//        Define ID
        mLevel = findViewById(R.id.level)
        loadingIn = findViewById(R.id.card_loading_in)

//        Set Variable SharedPreferences
        mLevel.text = sharedPreferences.getStringSharedPreferences(SP_LEVEL)

//        Action
        loadingIn.setOnClickListener {
            val intent = Intent(this@MenuActivity, LoadingInActivity::class.java)
            this@MenuActivity.startActivity(intent)
        }
    }
}