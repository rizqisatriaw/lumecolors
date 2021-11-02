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
import com.rizqi.lumecolorsapp.model.MHistory

class HistoryAdapter(private val mData: List<MHistory>, private val mContext: Context): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show_barang, parent, false)

        return ViewHolder(view, mContext)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHistory(mData[position])
    }
}

class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
    private val mImageBarang = view.findViewById<ImageView>(R.id.image_barang)
    private val mNama = view.findViewById<TextView>(R.id.name)

    fun bindHistory(data: MHistory) {
        Glide.with(context)
            .load(data.gambar)
            .fitCenter()
            .into(mImageBarang)

        mNama.text = data.nama_produk
    }
}