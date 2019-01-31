package com.freshworks.yana.Adapter.ViewHolder

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freshworks.yana.Interface.ItemClickListener
import com.freshworks.yana.Model.Article
import com.freshworks.yana.NewsDetailActivity
import com.freshworks.yana.R
import com.squareup.picasso.Picasso
import java.util.*

class ListNewsAdapter(val articleList:MutableList<Article> , private val context : Context) : RecyclerView.Adapter<ListNewsViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNewsViewHolder {


        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.layout_news_content_element,parent,false)
        return ListNewsViewHolder(itemView)


    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListNewsViewHolder, position: Int) {

        Picasso.get().load(articleList[position].urlToImage.toString()).into(holder.article_image);

        val content = articleList[position].title.toString()

        if(content.length>65)
        {
            holder.article_title.text =   "${content.subSequence(0,60)} ..."

        }

        if(articleList[position].publishedAt!=null)
        {
            holder.article_time.text = articleList[position].publishedAt.toString()
        }


    holder.setItemClickListener(object  : ItemClickListener{
        override fun onClick(view: View, position: Int) {
            val detailIntent = Intent(context,NewsDetailActivity::class.java)
            detailIntent.putExtra("WEBURL",articleList[position].url.toString())
            context.startActivity(detailIntent)

        }

    })


    }





}