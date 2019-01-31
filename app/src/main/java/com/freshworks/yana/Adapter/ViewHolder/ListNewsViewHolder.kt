package com.freshworks.yana.Adapter.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.freshworks.yana.Interface.ItemClickListener
import kotlinx.android.synthetic.main.layout_news_content_element.view.*

class ListNewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{

    private lateinit var itemClickListener: ItemClickListener
    var article_image = itemView.article_image
    var article_time = itemView.article_time
    var article_title = itemView.article_title

    init{
        itemView.setOnClickListener(
            this
        )
    }


    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {

        itemClickListener.onClick(v!!,adapterPosition)
    }


}