package com.rizqi.lumecolorsapp.main

import android.app.DatePickerDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.adapter.HistoryAdapter
import com.rizqi.lumecolorsapp.api.GetDataService
import com.rizqi.lumecolorsapp.api.RetrofitClients
import com.rizqi.lumecolorsapp.model.MHistory
import com.rizqi.lumecolorsapp.utils.Constants
import com.rizqi.lumecolorsapp.response.ResponseHistory
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class HistoryLoadingInActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: HistoryAdapter
    private lateinit var emptyState: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var datePicker: DatePickerDialog
    private lateinit var dateFrom: TextView
    private lateinit var dateTo: TextView
    private lateinit var mLoading: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_loading_in)

        emptyState = findViewById(R.id.empty_state)
        recyclerView = findViewById(R.id.rv_show)
        dateFrom = findViewById(R.id.date_from)
        dateTo = findViewById(R.id.date_to)

        mLoading = ProgressDialog(this@HistoryLoadingInActivity)
        mLoading.setCancelable(false)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateNow = "${year}-${month + 1}-${day}"

        dateFrom.text = dateNow
        dateTo.text = dateNow

        getListHistory(dateNow, dateNow)

        setDateRange(day, month, year)
    }
    
    private fun getListHistory(dari: String, sampai: String) {
        mLoading.setMessage("Loading...")
        mLoading.show()
        emptyState.visibility = View.GONE
        recyclerView.visibility = View.GONE
        
        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.listHistory(dari, sampai)

        call.enqueue(object : Callback<ResponseHistory> {

            override fun onFailure(call: Call<ResponseHistory>, t: Throwable) {

                Toast.makeText(
                    this@HistoryLoadingInActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("FAILED :", t.message.toString())

                emptyState.text = "Terjadi kesalahan saat memuat data."
                emptyState.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                mLoading.dismiss()
            }

            override fun onResponse(call: Call<ResponseHistory>, response: Response<ResponseHistory>) {

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

                    Log.d("DATASIZE: ", "${res.data.size}")

                } else {

                    Toast.makeText(
                        this@HistoryLoadingInActivity,
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

    private fun setRecyclerView(data: ArrayList<MHistory>) {
        linearLayoutManager = LinearLayoutManager(this@HistoryLoadingInActivity)
        mAdapter = HistoryAdapter(data, this@HistoryLoadingInActivity)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapter
        }
    }

    private fun setDateRange(day: Int, month: Int, year: Int) {
        dateFrom.setOnClickListener {
            datePicker = DatePickerDialog(this@HistoryLoadingInActivity,
                { view, year, month, dayOfMonth ->
                    dateFrom.text = "${year}-${month + 1}-${dayOfMonth}"
                }, year, month, day)
            datePicker.show()
        }

        dateFrom.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                Do Something
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Do Something
            }

            override fun afterTextChanged(s: Editable?) {
                getListHistory(dateFrom.text.toString(), dateTo.text.toString())
            }

        })

        dateTo.setOnClickListener {
            datePicker = DatePickerDialog(this@HistoryLoadingInActivity,
                { view, year, month, dayOfMonth ->
                    dateTo.text = "${year}-${month + 1}-${dayOfMonth}"
                }, year, month, day)
            datePicker.show()
        }

        dateTo.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                Do Something
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Do Something
            }

            override fun afterTextChanged(s: Editable?) {
                getListHistory(dateFrom.text.toString(), dateTo.text.toString())
            }

        })
    }
}