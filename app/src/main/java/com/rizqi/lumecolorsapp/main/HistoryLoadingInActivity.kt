package com.rizqi.lumecolorsapp.main

import android.app.DatePickerDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.adapter.HistoryAdapter
import com.rizqi.lumecolorsapp.adapter.QRListAdapter
import com.rizqi.lumecolorsapp.api.GetDataService
import com.rizqi.lumecolorsapp.api.RetrofitClients
import com.rizqi.lumecolorsapp.model.MHistory
import com.rizqi.lumecolorsapp.model.MListQR
import com.rizqi.lumecolorsapp.utils.Constants
import com.rizqi.lumecolorsapp.response.ResponseHistory
import com.rizqi.lumecolorsapp.response.ResponseListQR
import com.rizqi.lumecolorsapp.utils.Constants.LOADING_MSG
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class HistoryLoadingInActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: HistoryAdapter
    private lateinit var mAdapterQR: QRListAdapter
    private lateinit var datePicker: DatePickerDialog
    private lateinit var mLoading: ProgressDialog
//    Variable From Layout
    private lateinit var emptyState: TextView
    private lateinit var emptyStateQR: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var listQRShow: RecyclerView
    private lateinit var textDateFrom: TextView
    private lateinit var textDateTo: TextView
    private lateinit var imgDateFrom: ImageView
    private lateinit var imgDateTo: ImageView
    private lateinit var lytQrList: LinearLayout
    private lateinit var vBack: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_loading_in)


        mLoading = ProgressDialog(this@HistoryLoadingInActivity)
        mLoading.setCancelable(false)

//        Variable From Layout
        emptyState = findViewById(R.id.empty_state)
        emptyStateQR = findViewById(R.id.empty_state_qr)
        recyclerView = findViewById(R.id.rv_show)
        listQRShow = findViewById(R.id.list_qr_show)
        textDateFrom = findViewById(R.id.txt_date_from)
        textDateTo = findViewById(R.id.txt_date_to)
        imgDateFrom = findViewById(R.id.img_date_from)
        imgDateTo = findViewById(R.id.img_date_to)
        lytQrList = findViewById(R.id.layout_qr_list)
        vBack = findViewById(R.id.view_back)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateNow = "${year}-${month + 1}-${day}"

        textDateFrom.text = dateNow
        textDateTo.text = dateNow

        getListHistory(dateNow, dateNow)

        setDateRange(day, month, year)
    }
    
    private fun getListHistory(dari: String, sampai: String) {
        mLoading.setMessage(LOADING_MSG)
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

                emptyState.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                emptyState.text = "Terjadi kesalahan saat memuat data."

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
                        emptyState.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        emptyState.text = "Tidak ada data pada jangka waktu ini."
                    }

//                    Log.d("DATASIZE: ", "${res.data.size}")

                } else {

                    Toast.makeText(
                        this@HistoryLoadingInActivity,
                        "GAGAL",
                        Toast.LENGTH_LONG
                    ).show()

                    emptyState.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    emptyState.text = res.message
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

        vBack.setOnClickListener {
            lytQrList.visibility = View.GONE
        }

        mAdapter.interfaceClick(object: HistoryAdapter.BtnClickListener{
            override fun onBtnClick(data: MHistory) {
//                Log.d("BACOD: ", data.id)
                lytQrList.visibility = View.VISIBLE

                fetchListQR(data)
            }

        })
    }

    private fun fetchListQR(data: MHistory) {
        emptyStateQR.visibility = View.VISIBLE
        listQRShow.visibility = View.GONE
        emptyStateQR.text = "Memuat..."

        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.listQr(data.id)

        call.enqueue(object : Callback<ResponseListQR> {

            override fun onFailure(call: Call<ResponseListQR>, t: Throwable) {

                Toast.makeText(
                    this@HistoryLoadingInActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("FAILED :", t.message.toString())

                emptyStateQR.visibility = View.VISIBLE
                listQRShow.visibility = View.GONE
                emptyStateQR.text = "Terjadi kesalahan saat memuat data."
            }

            override fun onResponse(call: Call<ResponseListQR>, response: Response<ResponseListQR>) {

                val res = response.body()!!

                if (res.status == Constants.STAT200) {
                    if(res.data.size != 0) {
                        emptyStateQR.visibility = View.GONE
                        listQRShow.visibility = View.VISIBLE

                        setRecyclerQR(res.data)

                    } else {
                        emptyStateQR.visibility = View.VISIBLE
                        listQRShow.visibility = View.GONE
                        emptyStateQR.text = "Tidak ada data."
                    }


                } else {

                    Toast.makeText(
                        this@HistoryLoadingInActivity,
                        "GAGAL",
                        Toast.LENGTH_LONG
                    ).show()

                    emptyStateQR.visibility = View.VISIBLE
                    listQRShow.visibility = View.GONE
                    emptyStateQR.text = "${res.message}"
                }
            }

        })
    }

    private fun setRecyclerQR(data: ArrayList<MListQR>) {
        linearLayoutManager = LinearLayoutManager(this@HistoryLoadingInActivity)
        mAdapterQR = QRListAdapter(data, this@HistoryLoadingInActivity)
        listQRShow.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapterQR
        }
    }

    private fun setDateRange(day: Int, month: Int, year: Int) {
        imgDateFrom.setOnClickListener {
            datePicker = DatePickerDialog(this@HistoryLoadingInActivity,
                { view, year, month, dayOfMonth ->
                    textDateFrom.text = "${year}-${month + 1}-${dayOfMonth}"
                }, year, month, day)
            datePicker.show()
        }

        textDateFrom.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                Do Something
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Do Something
            }

            override fun afterTextChanged(s: Editable?) {
                getListHistory(textDateFrom.text.toString(), textDateTo.text.toString())
            }

        })

        imgDateTo.setOnClickListener {
            datePicker = DatePickerDialog(this@HistoryLoadingInActivity,
                { view, year, month, dayOfMonth ->
                    textDateTo.text = "${year}-${month + 1}-${dayOfMonth}"
                }, year, month, day)
            datePicker.show()
        }

        textDateTo.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                Do Something
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Do Something
            }

            override fun afterTextChanged(s: Editable?) {
                getListHistory(textDateFrom.text.toString(), textDateTo.text.toString())
            }

        })
    }
}