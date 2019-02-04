package com.test.hackernews.network

import com.test.hackernews.model.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("v0/topstories.json?print=pretty")
    fun getTopStories(): Call<List<Int>>

    @GET("v0/item/{articleid}.json?print=pretty")
    fun getArticle(@Path("articleid") id: Int): Call<ArticleResponse>
}