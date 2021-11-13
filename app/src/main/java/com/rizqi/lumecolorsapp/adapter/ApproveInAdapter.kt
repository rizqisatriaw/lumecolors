package com.rizqi.lumecolorsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.model.MApprove

class ApproveInAdapter(private val mData: List<MApprove>, private val mContext: Context): RecyclerView.Adapter<ViewHolderApprove>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderApprove {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_approve_in, parent, false)

        return ViewHolderApprove(view, mContext)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolderApprove, position: Int) {
        holder.bindData(mData[position])
    }
}

class ViewHolderApprove(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {

    fun bindData(data: MApprove) {
    }
}