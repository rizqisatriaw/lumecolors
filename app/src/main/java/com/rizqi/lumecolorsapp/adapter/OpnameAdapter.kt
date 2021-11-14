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
import com.rizqi.lumecolorsapp.model.MOpname
import com.rizqi.lumecolorsapp.utils.Constants

class OpnameAdapter(private val mData: List<MOpname>, private val mContext: Context): RecyclerView.Adapter<ViewHolderOpname>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOpname {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show_barang, parent, false)

        return ViewHolderOpname(view, mContext)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolderOpname, position: Int) {
        holder.bindData(mData[position])
    }
}

class ViewHolderOpname(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
    var mImage = view.findViewById<ImageView>(R.id.image_barang)
    var mName = view.findViewById<TextView>(R.id.name)
    var mDateEntry = view.findViewById<TextView>(R.id.date_entry_text)
    var mDateExp = view.findViewById<TextView>(R.id.exp_date_text)
    var mNoDelivery = view.findViewById<TextView>(R.id.no_delivery_text)
    var mNoBatch = view.findViewById<TextView>(R.id.no_batch_text)
    var mQty = view.findViewById<TextView>(R.id.qty)

    fun bindData(data: MOpname) {
        Glide.with(context)
            .load(Constants.URL_GAMBAR + data.gambar)
            .into(mImage)

        mName.text = data.nama_produk
        mDateEntry.text = data.nama_produk
        mDateExp.text = data.nama_produk
        mNoDelivery.text = data.nama_produk
        mNoBatch.text = data.nama_produk
        mQty.text = data.qty
    }
}