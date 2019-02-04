package com.test.hackernews.model

data class ArticleResponse(
    var by: String, var descendants: Int, var id: Int,
    var kids: List<Int>, var score: Int, var time: Long,
    var title: String, var type: String, var url: String, var text: String
)