package com.rizqi.lumecolorsapp.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.rizqi.lumecolorsapp.R
import android.text.Editable




class LoadingInActivity : AppCompatActivity() {
    private lateinit var imageHistory: ImageView
    private lateinit var dateEntry: EditText
    private lateinit var dateExp: EditText
    private lateinit var editProduct: EditText
    private lateinit var editDelivery: EditText
    private lateinit var editBatch: EditText
    private lateinit var editQtyPass: EditText
    private lateinit var editQtyReject: EditText
    private lateinit var checkDateEntry: ImageView
    private lateinit var checkDateExp: ImageView
    private lateinit var checkProduct: ImageView
    private lateinit var checkDelivery: ImageView
    private lateinit var checkBatch: ImageView
    private lateinit var checkPass: ImageView
    private lateinit var checkReject: ImageView
    private lateinit var btnSimpan: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_in)

        imageHistory = findViewById(R.id.img_history)
        dateEntry = findViewById(R.id.date_entry)
        dateExp = findViewById(R.id.date_exp)
        editProduct = findViewById(R.id.edit_product)
        editDelivery = findViewById(R.id.edit_delivery)
        editBatch = findViewById(R.id.edit_batch)
        editQtyPass = findViewById(R.id.edit_qty_pass)
        editQtyReject = findViewById(R.id.edit_qty_reject)
        checkDateEntry = findViewById(R.id.check_date_entry)
        checkDateExp = findViewById(R.id.check_exp)
        checkProduct = findViewById(R.id.check_product)
        checkDelivery = findViewById(R.id.check_delivery)
        checkBatch = findViewById(R.id.check_batch)
        checkPass = findViewById(R.id.check_pass)
        checkReject = findViewById(R.id.check_reject)
        btnSimpan = findViewById(R.id.button_simpan)

        imageHistory.setOnClickListener {
            val intent = Intent(this@LoadingInActivity, HistoryLoadingInActivity::class.java)
            this@LoadingInActivity.startActivity(intent)
        }

        btnSimpan.setOnClickListener {
            if(validationForm()) {
                loadingInSimpan()
            }
        }

        changeListener()
    }

    private fun changeListener() {
        dateEntry.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) checkDateEntry.visibility = View.VISIBLE
                else checkDateEntry.visibility = View.GONE
            }
        })

        dateExp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) checkDateExp.visibility = View.VISIBLE
                else checkDateExp.visibility = View.GONE
            }
        })

        editProduct.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) checkProduct.visibility = View.VISIBLE
                else checkProduct.visibility = View.GONE
            }
        })

        editDelivery.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) checkDelivery.visibility = View.VISIBLE
                else checkDelivery.visibility = View.GONE
            }
        })

        editBatch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) checkBatch.visibility = View.VISIBLE
                else checkBatch.visibility = View.GONE
            }
        })

        editQtyPass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) checkPass.visibility = View.VISIBLE
                else checkPass.visibility = View.GONE
            }
        })

        editQtyReject.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotEmpty()) checkReject.visibility = View.VISIBLE
                else checkReject.visibility = View.GONE
            }
        })
    }

    private fun validationForm(): Boolean {
        if (dateEntry.text?.isEmpty()!!) {
            dateEntry.error = "Enter Date"
            dateEntry.requestFocus()
            return false
        }

        if (dateExp.text?.isEmpty()!!) {
            dateExp.error = "Enter Date Exp"
            dateExp.requestFocus()
            return false
        }

        if (editProduct.text?.isEmpty()!!) {
            editProduct.error = "Enter Product"
            editProduct.requestFocus()
            return false
        }

        if (editDelivery.text?.isEmpty()!!) {
            editDelivery.error = "Enter Delivery"
            editDelivery.requestFocus()
            return false
        }

        if (editBatch.text?.isEmpty()!!) {
            editBatch.error = "Enter Batch"
            editBatch.requestFocus()
            return false
        }

        if (editQtyPass.text?.isEmpty()!!) {
            editQtyPass.error = "Enter Qty Pass"
            editQtyPass.requestFocus()
            return false
        }

        if (editQtyReject.text?.isEmpty()!!) {
            editQtyReject.error = "Enter Qty Reject"
            editQtyReject.requestFocus()
            return false
        }

        return true
    }

    private fun loadingInSimpan() {
        Toast.makeText(this@LoadingInActivity, "Do Something", Toast.LENGTH_SHORT).show()
    }
}