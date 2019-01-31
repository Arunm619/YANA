package com.freshworks.yana.Interface

import com.freshworks.yana.Model.News
import com.freshworks.yana.Model.Website
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

    @get:GET("v2/sources?apiKey=cdf8d957df6e4298989de6a7eaf2af59")

    val sources: Call<Website>

    @GET
    fun getNewsFromSource(@Url url:String) :Call<News>
}