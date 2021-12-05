package com.rizqi.lumecolorsapp.main

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.adapter.OpnameAdapter
import com.rizqi.lumecolorsapp.api.GetDataService
import com.rizqi.lumecolorsapp.api.RetrofitClients
import com.rizqi.lumecolorsapp.model.MOpname
import com.rizqi.lumecolorsapp.response.ResponseOpname
import com.rizqi.lumecolorsapp.utils.Constants
import com.rizqi.lumecolorsapp.utils.Constants.LOADING_MSG
import com.rizqi.lumecolorsapp.utils.Constants.PERIODE
import com.rizqi.lumecolorsapp.utils.Constants.URL_GAMBAR
import com.rizqi.lumecolorsapp.utils.Constants.URL_KARTU
import com.rizqi.lumecolorsapp.utils.SharedPreferencesUtils
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
    private lateinit var lytQrList: LinearLayout
    private lateinit var vBack: LinearLayout
    private lateinit var lnrImageShow: LinearLayout
    private lateinit var mImgShow: ImageView
    private lateinit var mImageSearch: ImageView
    private lateinit var lnrSearchView: LinearLayout
    private lateinit var etSearch: EditText
    private lateinit var rlHeader: RelativeLayout

    private lateinit var itemList: ArrayList<MOpname>
    private lateinit var searchItem: ArrayList<MOpname>

    var isDetail: Boolean = false
    var isImgShow: Boolean = false
    var isSearch: Boolean = false

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
//        lytQrList = findViewById(R.id.layout_qr)
//        vBack = findViewById(R.id.view_back)
        lnrImageShow = findViewById(R.id.linear_image_show)
        mImgShow = findViewById(R.id.image_show)
        mImageSearch = findViewById(R.id.logo_search)
        lnrSearchView = findViewById(R.id.search_view)
        etSearch = findViewById(R.id.edit_text_search)
        rlHeader = findViewById(R.id.header_title)

        itemList = ArrayList()
        searchItem = ArrayList()

        isDetail = false
        isImgShow = false
        isSearch = false

        mImageSearch.setOnClickListener {
            showSearch(true)
        }

        val periode = sharedPreferences.getStringSharedPreferences(PERIODE)!!

        datePeriod.text = periode

        getListOpname(periode)

        searchAction()
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

                    itemList = res.data

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

//        vBack.setOnClickListener {
//            lytQrList.visibility= View.GONE
//            isDetail = false
//        }

        mAdapter.interfaAction(object: OpnameAdapter.InterfaceAdapter{
            override fun onBtnClick(data: MOpname) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(URL_KARTU + data.id))
                this@StockOpnameActivity.startActivity(browserIntent)
            }

            override fun onBtnClickImage(data: MOpname) {
                lnrImageShow.visibility = View.VISIBLE
                isImgShow = true

                Glide.with(this@StockOpnameActivity)
                    .load(URL_GAMBAR + data.gambar)
                    .into(mImgShow)
            }

        })

    }

    private fun searchAction() {
        etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(isSearch && etSearch.text.isNotEmpty()) {
                    searchItem = ArrayList()

                    for (i in 0 until itemList.size) {
                        val item = itemList[i]
                        if(item.nama_produk.contains(etSearch.text, ignoreCase = true)) {
                            searchItem.add(item)
                        }
                    }

                    if(searchItem.size != 0) {
                        setRecyclerView(searchItem)

                        emptyState.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        emptyState.text = ""
                    } else {
                        emptyState.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        if(itemList.size == 0) emptyState.text = "Tidak ada data pada jangka waktu ini."
                        else emptyState.text = "Barang tidak ditemukan."
                    }
                } else if(isSearch && etSearch.text.isEmpty()) {
                    setRecyclerView(itemList)

                    if(itemList.size == 0) {
                        emptyState.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        emptyState.text = "Tidak ada data pada jangka waktu ini."
                    } else {
                        emptyState.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        emptyState.text = ""
                    }
                }
            }

        })
    }

    private fun showSearch(show: Boolean) {
        if(show) {
            isSearch = true
            lnrSearchView.visibility = View.VISIBLE
            rlHeader.visibility = View.GONE
        } else {
            isSearch = false
            lnrSearchView.visibility = View.GONE
            rlHeader.visibility = View.VISIBLE
        }
        etSearch.setText("")
    }

    override fun onBackPressed() {
        if(isImgShow) {
            isImgShow = false
            lnrImageShow.visibility = View.GONE
            return
        }
        if(isDetail) {
            isDetail = false
//            lytQrList.visibility = View.GONE
            return
        }
        if(isSearch) {
            showSearch(false)
            setRecyclerView(itemList)
            return
        }
        super.onBackPressed()
    }

}