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
import com.rizqi.lumecolorsapp.adapter.OpnameAdapter
import com.rizqi.lumecolorsapp.api.GetDataService
import com.rizqi.lumecolorsapp.api.RetrofitClients
import com.rizqi.lumecolorsapp.model.MOpname
import com.rizqi.lumecolorsapp.utils.Constants
import com.rizqi.lumecolorsapp.response.ResponseOpname
import com.rizqi.lumecolorsapp.utils.Constants.LOADING_MSG
import com.rizqi.lumecolorsapp.utils.Constants.PERIODE
import com.rizqi.lumecolorsapp.utils.SharedPreferencesUtils
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class StockOpnameActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferencesUtils
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: OpnameAdapter
    private lateinit var mLoading: ProgressDialog
    //    Variable From Layout
    private lateinit var emptyState: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var datePeriod: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_opname)

        sharedPreferences = SharedPreferencesUtils(this@StockOpnameActivity)
        mLoading = ProgressDialog(this@StockOpnameActivity)
        mLoading.setCancelable(false)

//        Variable From Layout
        emptyState = findViewById(R.id.empty_state)
        recyclerView = findViewById(R.id.rv_show)
        datePeriod = findViewById(R.id.periode)

        val periode = sharedPreferences.getStringSharedPreferences(PERIODE)!!

        datePeriod.text = periode

        getListOpname(periode)
    }

    private fun getListOpname(periode: String) {
        mLoading.setMessage(LOADING_MSG)
        mLoading.show()
        emptyState.visibility = View.GONE
        recyclerView.visibility = View.GONE

        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.opname(periode)

        call.enqueue(object : Callback<ResponseOpname> {

            override fun onFailure(call: Call<ResponseOpname>, t: Throwable) {

                Toast.makeText(
                    this@StockOpnameActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("FAILED :", t.message.toString())

                emptyState.text = "Terjadi kesalahan saat memuat data."
                emptyState.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                mLoading.dismiss()
            }

            override fun onResponse(call: Call<ResponseOpname>, response: Response<ResponseOpname>) {

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

//                    Log.d("DATASIZE: ", "${res.data.size}")

                } else {

                    Toast.makeText(
                        this@StockOpnameActivity,
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

    private fun setRecyclerView(data: ArrayList<MOpname>) {
        linearLayoutManager = LinearLayoutManager(this@StockOpnameActivity)
        mAdapter = OpnameAdapter(data, this@StockOpnameActivity)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapter
        }
    }

}