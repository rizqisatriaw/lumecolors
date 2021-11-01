package com.rizqi.lumecolorsapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryLoadingInActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: HistoryAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_loading_in)

        recyclerView = findViewById(R.id.rv_show)

        getListHistory()
    }
    
    private fun getListHistory() {
        val dari = "2021-10-01"
        val sampai = "2021-10-31"
        
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

            }

            override fun onResponse(call: Call<ResponseHistory>, response: Response<ResponseHistory>) {

                val res = response.body()!!

                if (res.status == Constants.STAT200) {

//                    Toast.makeText(
//                        this@HistoryLoadingInActivity,
//                        "BERHASIL",
//                        Toast.LENGTH_LONG
//                    ).show()

                    setRecyclerView(res.data)

                } else {

                    Toast.makeText(
                        this@HistoryLoadingInActivity,
                        "GAGAL",
                        Toast.LENGTH_LONG
                    ).show()

                }
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
}