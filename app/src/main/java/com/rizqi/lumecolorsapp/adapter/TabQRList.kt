package com.rizqi.lumecolorsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.model.MTabQR
import com.rizqi.lumecolorsapp.utils.Constants

class TabQRList(private val mData: List<MTabQR>, private val mContext: Context): RecyclerView.Adapter<ViewHolderTabQR>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTabQR {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qr_approve_out, parent, false)

        return ViewHolderTabQR(view, mContext)
    }

    override fun onBindViewHolder(holder: ViewHolderTabQR, position: Int) {
        holder.bindData(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}

class ViewHolderTabQR(view: View,private val mContext: Context): RecyclerView.ViewHolder(view) {
    var qrCode = view.findViewById<TextView>(R.id.qr_code)
    var produkName = view.findViewById<TextView>(R.id.produk_name)

    fun bindData(data: MTabQR) {
        qrCode.text = data.qrcode
        produkName.text = data.nama_produk
    }
}