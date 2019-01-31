package com.freshworks.yana

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.freshworks.yana.Adapter.ViewHolder.ListSourceAdapter
import com.freshworks.yana.Common.Common
import com.freshworks.yana.Interface.NewsService
import com.freshworks.yana.Model.Website
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    lateinit var layoutManager : LinearLayoutManager
    lateinit var mService: NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var alertDialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        alertDialog = SpotsDialog.Builder().setContext(this).build()


        //init cache db
        Paper.init(this)

        //init service
        mService = Common.newsService


        //init view




        rv_source_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        rv_source_news.layoutManager = layoutManager



       // Toast.makeText(this,"Hello ",Toast.LENGTH_SHORT).show()






        Log.i("MAINACTIVITY","SWIPED FOR FIRST TIME")


        swipe_refresh.post {
            swipe_refresh.isRefreshing= true;
            loadWebsiteSource(true)

        }

        swipe_refresh.setOnRefreshListener {
            loadWebsiteSource(true)

        }
    }


    private fun loadWebsiteSource(isRefresh: Boolean) {


        if(!isRefresh)
        {
            val cache = Paper.book().read<String>("CACHE")
            if((cache!=null) and (!cache.isBlank()) and (cache != "null"))
            {
                val website = Gson().fromJson<Website>(cache,Website::class.java)
                adapter = ListSourceAdapter(baseContext,website)
                adapter.notifyDataSetChanged()
                rv_source_news.adapter = adapter
            }
            else
            {

                alertDialog.show()

                mService.sources.enqueue(object : retrofit2.Callback<Website>{
                    override fun onFailure(call: Call<Website>, t: Throwable) {

                        Toast.makeText(baseContext,"FAILED",Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Website>, response: Response<Website>) {

                        adapter = ListSourceAdapter(baseContext,response.body()!! )
                        adapter.notifyDataSetChanged()
                        rv_source_news.adapter = adapter

                        Paper.book().write("CACHE",Gson().toJson(response.body()!!))

                        alertDialog.dismiss()
                    }

                })
            }
        }
        else
        {
            swipe_refresh.isRefreshing=true
            alertDialog.show()

            mService.sources.enqueue(object : retrofit2.Callback<Website>{
                override fun onFailure(call: Call<Website>, t: Throwable) {

                    Toast.makeText(baseContext,"FAILED",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Website>, response: Response<Website>) {

                    adapter = ListSourceAdapter(baseContext,response.body()!! )
                    adapter.notifyDataSetChanged()
                    rv_source_news.adapter = adapter

                    Paper.book().write("CACHE",Gson().toJson(response.body()!!))

                    swipe_refresh.isRefreshing=false
                   // alertDialog.dismiss()
                    alertDialog.dismiss()
                }

            })
        }




    }

}
