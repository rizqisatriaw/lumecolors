package com.rizqi.lumecolorsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.model.MStok

class StockAdapter(private val mData: List<MStok>, private val mContext: Context): RecyclerView.Adapter<ViewHolderStok>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderStok {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show_barang, parent, false)

        return ViewHolderStok(view, mContext)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolderStok, position: Int) {
        holder.bindData(mData[position])
    }
}

class ViewHolderStok(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
    var mName = view.findViewById<TextView>(R.id.name)
    var mDateEntry = view.findViewById<TextView>(R.id.date_entry_text)
    var mDateExp = view.findViewById<TextView>(R.id.exp_date_text)
    var mNoDelivery = view.findViewById<TextView>(R.id.no_delivery_text)
    var mNoBatch = view.findViewById<TextView>(R.id.no_batch_text)
    var mQty = view.findViewById<TextView>(R.id.qty)

    fun bindData(data: MStok) {
        mName.text = data.nama_produk
        mDateEntry.text = data.nama_produk
        mDateExp.text = data.nama_produk
        mNoDelivery.text = data.nama_produk
        mNoBatch.text = data.nama_produk
        mQty.text = data.qty
    }
}