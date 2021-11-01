package com.rizqi.lumecolorsapp.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import com.rizqi.lumecolorsapp.R

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val loadingIn = findViewById<RelativeLayout>(R.id.card_loading_in)

        loadingIn.setOnClickListener {
            val intent = Intent(this@MenuActivity, LoadingInActivity::class.java)
            this@MenuActivity.startActivity(intent)
        }

        validationForm()
    }

    val dateEntry = findViewById<EditText>(R.id.date_entry)
    val dateExp = findViewById<EditText>(R.id.date_exp)
    val editProduct = findViewById<EditText>(R.id.edit_product)
    val editDelivery = findViewById<EditText>(R.id.edit_delivery)
    val editBatch = findViewById<EditText>(R.id.edit_batch)
    val editQtyPass = findViewById<EditText>(R.id.edit_qty_pass)
    val editQtyReject = findViewById<EditText>(R.id.edit_qty_reject)
    val checkDateEntry = findViewById<ImageView>(R.id.check_date_entry)
    val checkDateExp = findViewById<ImageView>(R.id.check_exp)
    val checkProduct = findViewById<ImageView>(R.id.check_product)
    val checkDelivery = findViewById<ImageView>(R.id.check_delivery)
    val checkBatch = findViewById<ImageView>(R.id.check_batch)
    val checkPass = findViewById<ImageView>(R.id.check_pass)
    val checkReject = findViewById<ImageView>(R.id.check_reject)

    fun validationForm(): Boolean {
        if (dateEntry?.text?.isEmpty()!!) {
            dateEntry.error = "Enter Date"
            dateEntry.requestFocus()
            return false
        }
        if (dateEntry.length() > 0) {
            checkDateEntry.visibility = View.VISIBLE
            return false
        }
        if (dateExp?.text?.isEmpty()!!) {
            dateExp.error = "Enter Date Exp"
            dateExp.requestFocus()
            return false
        }
        if (dateExp.length() > 0) {
            checkDateExp.visibility = View.VISIBLE
            return false
        }
        if (editProduct?.text?.isEmpty()!!) {
            editProduct.error = "Enter Product"
            editProduct.requestFocus()
            return false
        }
        if (editProduct.length() > 0) {
            checkProduct.visibility = View.VISIBLE
            return false
        }
        if (editDelivery?.text?.isEmpty()!!) {
            editDelivery.error = "Enter Delivery"
            editDelivery.requestFocus()
            return false
        }
        if (editDelivery.length() > 0) {
            checkDelivery.visibility = View.VISIBLE
            return false
        }
        if (editBatch?.text?.isEmpty()!!) {
            editBatch.error = "Enter Batch"
            editBatch.requestFocus()
            return false
        }
        if (editBatch.length() > 0) {
            checkBatch.visibility = View.VISIBLE
            return false
        }
        if (editQtyPass?.text?.isEmpty()!!) {
            editQtyPass.error = "Enter Qty Pass"
            editQtyPass.requestFocus()
            return false
        }
        if (editQtyPass.length() > 0) {
            checkPass.visibility = View.VISIBLE
            return false
        }
        if (editQtyReject?.text?.isEmpty()!!) {
            editQtyReject.error = "Enter Qty Reject"
            editQtyReject.requestFocus()
            return false
        }
        if (editQtyReject.length() > 0) {
            checkReject.visibility = View.VISIBLE
            return false
        }

        return true
    }
}