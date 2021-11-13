package com.rizqi.lumecolorsapp.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.utils.Constants
import com.rizqi.lumecolorsapp.utils.Constants.CHECKER
import com.rizqi.lumecolorsapp.utils.Constants.SP_LEVEL
import com.rizqi.lumecolorsapp.utils.SharedPreferencesUtils

class MenuActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferencesUtils

    private lateinit var mLevel: TextView
    private lateinit var history: RelativeLayout
    private lateinit var approveIn: RelativeLayout
    private lateinit var approveOut: RelativeLayout
    private lateinit var stockIn: RelativeLayout
    private lateinit var stockOut: RelativeLayout
    private lateinit var stockOpname: RelativeLayout
    private lateinit var hiddenStockOpname: View
    private lateinit var hiddenStockOut: View
    private lateinit var hiddenStockIn: View
    private lateinit var hiddenApproveIn: View
    private lateinit var hiddenApproveOut: View
    private lateinit var hiddenHistory: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

//        Define Variable
        sharedPreferences = SharedPreferencesUtils(this@MenuActivity)

//        Define ID
        mLevel = findViewById(R.id.level)
        history = findViewById(R.id.card_history)
        approveIn = findViewById(R.id.card_approve_in)
        approveOut = findViewById(R.id.card_approve_out)
        stockIn = findViewById(R.id.card_stock_in)
        stockOut = findViewById(R.id.card_stock_out)
        stockOpname = findViewById(R.id.card_stock_opname)
        hiddenStockOpname = findViewById(R.id.hidden_stock_opname)
        hiddenStockIn = findViewById(R.id.hidden_stock_in)
        hiddenStockOut = findViewById(R.id.hidden_stock_out)
        hiddenApproveIn = findViewById(R.id.hidden_approve_in)
        hiddenApproveOut = findViewById(R.id.hidden_approve_out)


//        Set Variable SharedPreferences
        mLevel.text = sharedPreferences.getStringSharedPreferences(SP_LEVEL)

//        Set LEVEL Access
        if (SP_LEVEL == "CHECKER"){
            hiddenApproveIn.visibility
        }
        if (SP_LEVEL == "PACKING" || SP_LEVEL == "SENDER"){
            hiddenHistory.visibility
            hiddenApproveIn.visibility
            hiddenStockIn.visibility
            hiddenStockOut.visibility
            hiddenStockOpname.visibility
        }

//        Action
        history.setOnClickListener {
            val intent = Intent(this@MenuActivity, LoadingInActivity::class.java)
            this@MenuActivity.startActivity(intent)
        }

        approveIn.setOnClickListener {
            val intent = Intent(this@MenuActivity, ApproveInActivity::class.java)
            this@MenuActivity.startActivity(intent)
        }

        stockIn.setOnClickListener {
            val intent = Intent(this@MenuActivity, StockInActivity::class.java)
            this@MenuActivity.startActivity(intent)
        }

    }
}