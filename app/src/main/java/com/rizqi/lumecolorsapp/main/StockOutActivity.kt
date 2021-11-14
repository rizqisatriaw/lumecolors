package com.rizqi.lumecolorsapp.main

import android.app.DatePickerDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.adapter.StockAdapter
import com.rizqi.lumecolorsapp.api.GetDataService
import com.rizqi.lumecolorsapp.api.RetrofitClients
import com.rizqi.lumecolorsapp.model.MStok
import com.rizqi.lumecolorsapp.response.ResponseStok
import com.rizqi.lumecolorsapp.utils.Constants
import com.rizqi.lumecolorsapp.utils.Constants.STOCK_OUT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class StockOutActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: StockAdapter
    private lateinit var datePicker: DatePickerDialog
    private lateinit var mLoading: ProgressDialog
//    Variable From Layout
    private lateinit var emptyState: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var txtDateFrom: TextView
    private lateinit var txtDateTo: TextView
    private lateinit var imgDateFrom: ImageView
    private lateinit var imgDateTo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_out)

        mLoading = ProgressDialog(this@StockOutActivity)
        mLoading.setCancelable(false)

//        Variable From Layout
        emptyState = findViewById(R.id.empty_state)
        recyclerView = findViewById(R.id.rv_show)
        txtDateFrom = findViewById(R.id.txt_date_from)
        txtDateTo = findViewById(R.id.txt_date_to)
        imgDateFrom = findViewById(R.id.img_date_from)
        imgDateTo = findViewById(R.id.img_date_to)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateNow = "${year}-${month + 1}-${day}"

        txtDateFrom.text = dateNow
        txtDateTo.text = dateNow

        getListStockOut(dateNow, dateNow)

        setDateRange(day, month, year)
    }

    private fun getListStockOut(dari: String, sampai: String) {
        mLoading.setMessage(Constants.LOADING_MSG)
        mLoading.show()

        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.stokOut(dari, sampai)

        call.enqueue(object : Callback<ResponseStok> {

            override fun onFailure(call: Call<ResponseStok>, t: Throwable) {

                Toast.makeText(
                    this@StockOutActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("FAILED :", t.message.toString())

                emptyState.text = "Terjadi kesalahan saat memuat data."
                emptyState.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                mLoading.dismiss()
            }

            override fun onResponse(call: Call<ResponseStok>, response: Response<ResponseStok>) {

                val res = response.body()!!

                if (res.status == Constants.STAT200) {

                    if(res.data.size != 0) {
                        emptyState.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        setRecyclerView(res.data)
                    } else {
                        emptyState.text = "Tidak ada data pada jangka waktu ini."
                        emptyState.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }

                } else {

                    Toast.makeText(
                        this@StockOutActivity,
                        "GAGAL",
                        Toast.LENGTH_LONG
                    ).show()

                    emptyState.text = res.message
                    emptyState.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE

                }

                mLoading.dismiss()
            }

        })
    }

    private fun setRecyclerView(data: ArrayList<MStok>) {
        linearLayoutManager = LinearLayoutManager(this@StockOutActivity)
        mAdapter = StockAdapter(data, this@StockOutActivity, STOCK_OUT)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapter
        }
    }

    private fun setDateRange(day: Int, month: Int, year: Int) {
        imgDateFrom.setOnClickListener {
            datePicker = DatePickerDialog(this@StockOutActivity,
                { view, year, month, dayOfMonth ->
                    txtDateFrom.text = "${year}-${month + 1}-${dayOfMonth}"
                }, year, month, day)
            datePicker.show()
        }

        txtDateFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                Do Something
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Do Something
            }

            override fun afterTextChanged(s: Editable?) {
                getListStockOut(txtDateFrom.text.toString(), txtDateTo.text.toString())
            }

        })

        imgDateTo.setOnClickListener {
            datePicker = DatePickerDialog(this@StockOutActivity,
                { view, year, month, dayOfMonth ->
                    txtDateTo.text = "${year}-${month + 1}-${dayOfMonth}"
                }, year, month, day)
            datePicker.show()
        }

        txtDateTo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                Do Something
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Do Something
            }

            override fun afterTextChanged(s: Editable?) {
                getListStockOut(txtDateFrom.text.toString(), txtDateTo.text.toString())
            }

        })
    }
}