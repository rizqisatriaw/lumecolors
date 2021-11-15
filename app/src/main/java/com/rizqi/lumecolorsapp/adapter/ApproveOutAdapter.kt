package com.rizqi.lumecolorsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.model.MApprove

class ApproveOutAdapter(private val mData: List<MApprove>, private val mContext: Context): RecyclerView.Adapter<ViewHolderApprove>() {

    var interfaceAdapter: ApproveOutAdapter.InterfaceAdapter? = null

    fun interfaAction(interfaces: ApproveOutAdapter.InterfaceAdapter) {
        this.interfaceAdapter = interfaces
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderApprove {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_approve_in, parent, false)

        return ViewHolderApprove(view, mContext)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolderApprove, position: Int) {
        holder.bindData(mData[position])
    }

    interface InterfaceAdapter {
        fun onBtnClick(data: MApprove)
        fun onBtnClickImage(data: MApprove)
    }
}

class ViewHolderApprove(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
    var mMerchant = view.findViewById<TextView>(R.id.merchant_text)
    var mOrder = view.findViewById<TextView>(R.id.no_order_text)
    var mCustomer = view.findViewById<TextView>(R.id.customer_text)
    var mCart = view.findViewById<TextView>(R.id.cart_text)
    var mTanggal = view.findViewById<TextView>(R.id.tgl_validasi)
    var btnQR = view.findViewById<LinearLayout>(R.id.button_list_qr)

    fun bindData(data: MApprove) {
    }
}