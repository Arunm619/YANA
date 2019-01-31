package com.freshworks.yana.Adapter.ViewHolder

import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.View
import com.freshworks.yana.Interface.ItemClickListener
import kotlinx.android.synthetic.main.layout_source_news_single_item.view.*

class ListSourceViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView) , View.OnClickListener {


  private    lateinit var itemClickListener: ItemClickListener
    var source_title = itemView.tv_source_news_title



    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {

        itemClickListener.onClick(v!!,adapterPosition)

    }


}