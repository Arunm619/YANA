package com.freshworks.yana.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.freshworks.yana.ListNewsActivity
import com.freshworks.yana.Model.Website
import com.freshworks.yana.R
import kotlinx.android.synthetic.main.activity_main.*

class ListSourceAdapter(private val context: Context ,
                        private val website:Website )
    : RecyclerView.Adapter<ListSourceViewHolder>()

{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSourceViewHolder {

        var inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.layout_source_news_single_item, parent,false)
        return ListSourceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return website.sources!!.size
    }

    override fun onBindViewHolder(holder: ListSourceViewHolder, position: Int) {


        holder.source_title.text = website.sources!![position].name

        holder.itemView.setOnClickListener {
         //   Toast.makeText(context, "Awesome you ahave pressed $position", Toast.LENGTH_SHORT).show()

            val intent = Intent (context, ListNewsActivity::class.java)
            intent.putExtra("source", website.sources[position].id)
            context.startActivity(intent)

        }


    }


}