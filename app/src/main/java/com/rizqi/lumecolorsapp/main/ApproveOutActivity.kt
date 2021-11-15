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
import com.bumptech.glide.Glide
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.adapter.ApproveOutAdapter
import com.rizqi.lumecolorsapp.adapter.QRListAdapter
import com.rizqi.lumecolorsapp.api.GetDataService
import com.rizqi.lumecolorsapp.api.RetrofitClients
import com.rizqi.lumecolorsapp.model.MApprove
import com.rizqi.lumecolorsapp.model.MListQR
import com.rizqi.lumecolorsapp.utils.Constants
import com.rizqi.lumecolorsapp.response.ResponseApprove
import com.rizqi.lumecolorsapp.response.ResponseListQR
import com.rizqi.lumecolorsapp.utils.Constants.LOADING_MSG
import com.rizqi.lumecolorsapp.utils.Constants.URL_GAMBAR
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class ApproveOutActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: ApproveOutAdapter
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
    private lateinit var lnrChooseQr: LinearLayout
    private lateinit var vBack: LinearLayout
    private lateinit var lnrImageShow: LinearLayout
    private lateinit var mImgShow: ImageView
    private lateinit var btnAlamat: LinearLayout
    private lateinit var lnrAlamatView: LinearLayout
    var isDetail: Boolean = false
    var isImgShow: Boolean = false
    var isAlamatShow: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approve_out)


        mLoading = ProgressDialog(this@ApproveOutActivity)
        mLoading.setCancelable(false)

//        Variable From Layout
        emptyState = findViewById(R.id.empty_state)
        emptyStateQR = findViewById(R.id.empty_state_qr)
        recyclerView = findViewById(R.id.rv_show)
//        listQRShow = findViewById(R.id.list_qr_show)
        textDateFrom = findViewById(R.id.txt_date_from)
        textDateTo = findViewById(R.id.txt_date_to)
        imgDateFrom = findViewById(R.id.img_date_from)
        imgDateTo = findViewById(R.id.img_date_to)
        lnrChooseQr = findViewById(R.id.layout_pilih_qr)
        vBack = findViewById(R.id.view_back)
        lnrImageShow = findViewById(R.id.linear_image_show)
        mImgShow = findViewById(R.id.image_show)
        btnAlamat = findViewById(R.id.button_alamat)
        lnrAlamatView = findViewById(R.id.layout_alamat_view)
        isDetail = false
        isImgShow = false
        isAlamatShow = false

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateNow = "${year}-${month + 1}-${day}"

        textDateFrom.text = dateNow
        textDateTo.text = dateNow

        btnAlamat.setOnClickListener {
            lnrAlamatView.visibility = View.VISIBLE
            isAlamatShow = true
        }

//        getListHistory(dateNow, dateNow)

        setDateRange(day, month, year)
    }

    private fun getListHistory(dari: String, sampai: String) {
        mLoading.setMessage(LOADING_MSG)
        mLoading.show()

        emptyState.visibility = View.GONE
        recyclerView.visibility = View.GONE

        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.listApprove()

        call.enqueue(object : Callback<ResponseApprove> {

            override fun onFailure(call: Call<ResponseApprove>, t: Throwable) {

                Toast.makeText(
                    this@ApproveOutActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("FAILED :", t.message.toString())

                emptyState.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                emptyState.text = "Terjadi kesalahan saat memuat data."

                mLoading.dismiss()
            }

            override fun onResponse(call: Call<ResponseApprove>, response: Response<ResponseApprove>) {

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
                        this@ApproveOutActivity,
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

    private fun setRecyclerView(data: ArrayList<MApprove>) {
        linearLayoutManager = LinearLayoutManager(this@ApproveOutActivity)
        mAdapter = ApproveOutAdapter(data, this@ApproveOutActivity)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapter
        }

        vBack.setOnClickListener {
            lnrChooseQr.visibility = View.GONE
            isDetail = false
        }

        mAdapter.interfaAction(object: ApproveOutAdapter.InterfaceAdapter{
            override fun onBtnClick(data: MApprove) {
//                Log.d("BACOD: ", data.id)
                lnrChooseQr.visibility = View.VISIBLE
                isDetail = true

                fetchListQR(data)
            }

            override fun onBtnClickImage(data: MApprove) {
                lnrImageShow.visibility = View.VISIBLE
                isImgShow = true

                Glide.with(this@ApproveOutActivity)
                    .load(URL_GAMBAR + data.gambar)
                    .into(mImgShow)
            }

        })
    }

    private fun fetchListQR(data: MApprove) {
        emptyStateQR.visibility = View.VISIBLE
        listQRShow.visibility = View.GONE
        emptyStateQR.text = "Memuat..."

        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.listQr(data.id)

        call.enqueue(object : Callback<ResponseListQR> {

            override fun onFailure(call: Call<ResponseListQR>, t: Throwable) {

                Toast.makeText(
                    this@ApproveOutActivity,
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
                        this@ApproveOutActivity,
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
        linearLayoutManager = LinearLayoutManager(this@ApproveOutActivity)
        mAdapterQR = QRListAdapter(data, this@ApproveOutActivity)
        listQRShow.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapterQR
        }
    }

    private fun setDateRange(day: Int, month: Int, year: Int) {
        imgDateFrom.setOnClickListener {
            datePicker = DatePickerDialog(this@ApproveOutActivity,
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
//                getListHistory(textDateFrom.text.toString(), textDateTo.text.toString())
            }

        })

        imgDateTo.setOnClickListener {
            datePicker = DatePickerDialog(this@ApproveOutActivity,
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
//                getListHistory(textDateFrom.text.toString(), textDateTo.text.toString())
            }

        })
    }

    override fun onBackPressed() {
        if(isImgShow) {
            isImgShow = false
            lnrImageShow.visibility = View.GONE
            return
        }
        if(isDetail) {
            isDetail = false
            lnrChooseQr.visibility = View.GONE
            return
        }
        if(isAlamatShow) {
            isAlamatShow = false
            lnrAlamatView.visibility = View.GONE
            return
        }
        super.onBackPressed()
    }
}