package com.exnor.vray.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exnor.vray.R
import com.exnor.vray.bean.ConnectStatus
import com.exnor.vray.bean.VpnItemBean
import com.exnor.vray.helper.VpnConnectMgr

/**
created by edison 2020/3/16
 */
class VpnListAdapter: RecyclerView.Adapter<VpnListAdapter.VpnVH>() {

    val dataList = ArrayList<VpnItemBean>()
    var itemClickListener: VpnItemListener? = null

    interface VpnItemListener{
        fun onItemClicked(position: Int)
    }

    fun updateDatas(list: List<VpnItemBean>){
        if (list.isNotEmpty()){
            dataList.clear()
            dataList.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VpnVH {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_vpn, parent, false)
        return VpnVH(root)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: VpnVH, position: Int) {
        val context = holder.itemView.context
        val resource = context.resources
        val itemBean = dataList[position]
        Glide.with(context)
                .load(itemBean.country_url)
                .error(R.drawable.united_nations)
                .into(holder.ivCountry)
        holder.tvCountry.text = itemBean.countryName

        if (itemBean.isSelected){
            holder.cvItem.setBackgroundColor(resource.getColor(R.color.theme_green))
            holder.tvCountry.setTextColor(resource.getColor(R.color.color_white))
        }else{
            holder.cvItem.setBackgroundColor(resource.getColor(R.color.color_eeeeee))
            holder.tvCountry.setTextColor(resource.getColor(R.color.color_222222))
        }

        holder.cvItem.setOnClickListener {
            when (VpnConnectMgr.curStatus) {
                ConnectStatus.STOPPED -> {
                    itemClickListener?.onItemClicked(position)
                }

                ConnectStatus.CONNECTING -> {
                    Toast.makeText(context,
                            context.getString(R.string.str_just_connecting),Toast.LENGTH_LONG).show()
                }

                ConnectStatus.CONNECTED -> {
                    Toast.makeText(context,
                            context.getString(R.string.str_stopped_first),Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    class VpnVH(rootView: View): RecyclerView.ViewHolder(rootView){
        val cvItem = rootView.findViewById<LinearLayout>(R.id.cv_item)
        val ivCountry = rootView.findViewById<ImageView>(R.id.iv_country)
        val tvCountry = rootView.findViewById<TextView>(R.id.tv_country)
    }

}