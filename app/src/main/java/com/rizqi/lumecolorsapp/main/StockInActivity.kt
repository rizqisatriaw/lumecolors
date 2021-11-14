package com.rizqi.lumecolorsapp.main

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.rizqi.lumecolorsapp.utils.Constants.LOADING_MSG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockInActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: StockAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var mLoading: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_in)

        recyclerView = findViewById(R.id.rv_show)
        mLoading = ProgressDialog(this@StockInActivity)
        mLoading.setCancelable(false)

        getListStockIn()
    }

    private fun getListStockIn() {
        mLoading.setMessage(LOADING_MSG)
        mLoading.show()

        val dari = "2021-10-01"
        val sampai = "2021-10-31"

        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.stokIn(dari, sampai)

        call.enqueue(object : Callback<ResponseStok> {

            override fun onFailure(call: Call<ResponseStok>, t: Throwable) {

                Toast.makeText(
                    this@StockInActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("FAILED :", t.message.toString())

                mLoading.dismiss()
            }

            override fun onResponse(call: Call<ResponseStok>, response: Response<ResponseStok>) {

                val res = response.body()!!

                if (res.status == Constants.STAT200) {

                    setRecyclerView(res.data)

                } else {

                    Toast.makeText(
                        this@StockInActivity,
                        "GAGAL",
                        Toast.LENGTH_LONG
                    ).show()

                }

                mLoading.dismiss()
            }

        })
    }

    private fun setRecyclerView(data: ArrayList<MStok>) {
        linearLayoutManager = LinearLayoutManager(this@StockInActivity)
        mAdapter = StockAdapter(data, this@StockInActivity)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapter
        }
    }
}