package com.freshworks.yana

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.freshworks.yana.Adapter.ViewHolder.ListNewsAdapter
import com.freshworks.yana.Common.Common
import com.freshworks.yana.Interface.NewsService
import com.freshworks.yana.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class ListNewsActivity : AppCompatActivity() {


    var source = ""
    var webHotURL =""
    lateinit var adapter: ListNewsAdapter
    lateinit var dialog: android.app.AlertDialog
    lateinit var mService: NewsService
    lateinit var layoutManager: LinearLayoutManager




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_news)


        //init service
        mService = Common.newsService

        dialog = SpotsDialog.Builder().setContext(this).build()


        swipe_refresh_news.setOnRefreshListener {
            loadNews(source,true)
        }


        diagonalLayout.setOnClickListener {
            val detailIntent = Intent(this,NewsDetailActivity::class.java)
            detailIntent.putExtra("WEBURL",webHotURL)
            this.startActivity(detailIntent)
        }


        rv_list_news.setHasFixedSize(true)
        rv_list_news.layoutManager = LinearLayoutManager(this)


        if(intent != null)
        {
            source = intent.getStringExtra("source" )
            if(!source.isEmpty())
            {
                loadNews(source,false)
            }
        }
    }

    private fun loadNews(source: String?, isRefreshed: Boolean) {

        if(isRefreshed)
        {
            dialog.show()


            mService.getNewsFromSource(Common.getNewsAPI(source!!)).enqueue(
                object : retrofit2.Callback<News>{
                    override fun onFailure(call: Call<News>, t: Throwable) {

                        Toast.makeText(baseContext,"failed",Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        dialog.dismiss()

                        val locationofImage = response.body()!!.articles!![0].urlToImage.toString()
                        Picasso.get().load(locationofImage).into(topImage);


                        top_title . text = response.body()!!.articles!![0].title.toString()
                        topAuthor . text = response.body()!!.articles!![0].author.toString()
                        webHotURL = response.body()!!.articles!![0].url.toString()

                        val removeFirstItem = response.body()!!.articles
                        removeFirstItem!!.removeAt(0)
                        adapter = ListNewsAdapter(removeFirstItem,baseContext)
                        adapter.notifyDataSetChanged()
                        rv_list_news.adapter = adapter







                    }

                }
            )

        }
        else
        {
            swipe_refresh_news.isRefreshing= true



            mService.getNewsFromSource(Common.getNewsAPI(source!!)).enqueue(
                object : retrofit2.Callback<News>{
                    override fun onFailure(call: Call<News>, t: Throwable) {

                        Toast.makeText(baseContext,"failed",Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                       // dialog.dismiss()

                        swipe_refresh_news.isRefreshing= false

                        val locationofImage = response.body()!!.articles!![0].urlToImage.toString()
                        Picasso.get().load(locationofImage).into(topImage);


                        top_title . text = response.body()!!.articles!![0].title.toString()
                        topAuthor . text = response.body()!!.articles!![0].author.toString()
                        webHotURL = response.body()!!.articles!![0].url.toString()

                        val removeFirstItem = response.body()!!.articles
                        removeFirstItem!!.removeAt(0)
                        adapter = ListNewsAdapter(removeFirstItem,baseContext)
                        adapter.notifyDataSetChanged()
                        rv_list_news.adapter = adapter







                    }

                }
            )

        }
    }
}
