package com.rizqi.lumecolorsapp.main

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.auth.LoginActivity
import com.rizqi.lumecolorsapp.utils.Constants
import com.rizqi.lumecolorsapp.utils.Constants.CHECKER
import com.rizqi.lumecolorsapp.utils.Constants.SP_LEVEL
import com.rizqi.lumecolorsapp.utils.SharedPreferencesUtils

class MenuActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferencesUtils

    private lateinit var mLevel: TextView
    private lateinit var btnLogout: ImageView
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
        btnLogout = findViewById(R.id.button_logout)
        history = findViewById(R.id.card_history)
//        approveIn = findViewById(R.id.card_approve_in)
        approveOut = findViewById(R.id.card_approve_out)
        stockIn = findViewById(R.id.card_stock_in)
        stockOut = findViewById(R.id.card_stock_out)
        stockOpname = findViewById(R.id.card_stock_opname)
        hiddenStockOpname = findViewById(R.id.hidden_stock_opname)
        hiddenStockIn = findViewById(R.id.hidden_stock_in)
        hiddenStockOut = findViewById(R.id.hidden_stock_out)
//        hiddenApproveIn = findViewById(R.id.hidden_approve_in)
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
            val intent = Intent(this@MenuActivity, HistoryLoadingInActivity::class.java)
            this@MenuActivity.startActivity(intent)
        }

//        approveIn.setOnClickListener {
//            val intent = Intent(this@MenuActivity, ApproveInActivity::class.java)
//            this@MenuActivity.startActivity(intent)
//        }

        stockIn.setOnClickListener {
            val intent = Intent(this@MenuActivity, StockInActivity::class.java)
            this@MenuActivity.startActivity(intent)
        }

        stockOut.setOnClickListener {
            val intent = Intent(this@MenuActivity, StockOutActivity::class.java)
            this@MenuActivity.startActivity(intent)
        }

        stockOpname.setOnClickListener {
            val intent = Intent(this@MenuActivity, StockOpnameActivity::class.java)
            this@MenuActivity.startActivity(intent)
        }

        btnLogout.setOnClickListener {
            showDialogLogout()
        }

    }

    private fun showDialogLogout() {
        val dialog = Dialog(this@MenuActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_logout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val btnNo = dialog.findViewById<Button>(R.id.button_no)
        val btnYes = dialog.findViewById<Button>(R.id.button_yes)

        btnNo.setOnClickListener { dialog.dismiss() }
        btnYes.setOnClickListener { processLogout(dialog) }

        dialog.show()
    }

    private fun processLogout(dialog: Dialog) {
        setDataUser(SP_LEVEL, 0, "")
        setDataUser(Constants.PERIODE, 0, "")
        setDataUser(Constants.LOGGED_STATE, 0, Constants.LOGGED_OUT)

        dialog.dismiss()

        val intent = Intent(this@MenuActivity, LoginActivity::class.java)
        this@MenuActivity.startActivity(intent)
        finish()
        
    }

    fun setDataUser(key: String, int: Int, string: String){
        if (string != ""){
            sharedPreferences.setSharedPreferences(key, string)
        } else {
            sharedPreferences.setSharedPreferences(key, int)
        }
    }
}