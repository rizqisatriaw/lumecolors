package com.rizqi.lumecolorsapp.main

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.utils.Constants.SP_USERNAME
import com.rizqi.lumecolorsapp.utils.SharedPreferencesUtils

class MenuActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferencesUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

//        Identifier Variable
        sharedPreferences = SharedPreferencesUtils(this@MenuActivity)


//        Identifier ID
        val mUsername = findViewById<TextView>(R.id.username)

        val mHistory = findViewById<RelativeLayout>(R.id.rl_history)
        val mLoadingIn = findViewById<RelativeLayout>(R.id.rl_loadingIn)

        mUsername.text = sharedPreferences.getStringSharedPreferences(SP_USERNAME).toString()

        mHistory.setOnClickListener {
            this.startActivity(Intent(this@MenuActivity, HistoryLoadingInActivity::class.java))
        }

        mLoadingIn.setOnClickListener {
            this.startActivity(Intent(this@MenuActivity, LoadingInActivity::class.java))
        }
    }
}