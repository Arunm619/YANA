package com.freshworks.yana.Common

import com.freshworks.yana.Interface.NewsService
import com.freshworks.yana.Remote.RetrofitClient

object Common{
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "cdf8d957df6e4298989de6a7eaf2af59"


    val newsService : NewsService
    get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)


    fun getNewsAPI(source:String):String{

        val apiURL =
             StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
                 .append(API_KEY).toString()


        return apiURL 
    }
}

//curl 'https://api.github.com/users/arunm619?client_id=645c12594e51c8e53c10&client_secret=8f9ead704b23bc4fddd84711c529efafed276ab8'