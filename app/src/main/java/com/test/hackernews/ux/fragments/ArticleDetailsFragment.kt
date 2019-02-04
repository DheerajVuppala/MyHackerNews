package com.test.hackernews.ux.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.hackernews.R
import com.test.hackernews.model.ArticleResponse
import com.test.hackernews.network.ApiInterface
import com.test.hackernews.network.getClient
import kotlinx.android.synthetic.main.comment_item_holder.view.*
import kotlinx.android.synthetic.main.fragment_article_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ArticleDetailsFragment : Fragment() {

    private lateinit var articleResponse: ArticleResponse

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderView()
        if (articleResponse.kids != null) {
            fetchComments()
        }
    }

    private fun fetchComments() {
        val apiInterface = getClient()?.create(ApiInterface::class.java)
        articleResponse.kids.forEach {
            apiInterface?.getArticle(it)?.enqueue(object : Callback<ArticleResponse> {
                override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                    val text = response.body()?.text

                    Log.d("TEST123", "TEST: $text")

                    response.body()?.let { it1 -> renderArticleComments(it1) }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.common_network_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }


    }

    private fun renderArticleComments(response: ArticleResponse) {
        if(response.text == null) {
            return
        }
        val childItemData = layoutInflater.inflate(R.layout.comment_item_holder, null)
        childItemData.top = 100
        childItemData.comment_msg.text = response.text
        comments_view.addView(childItemData)
    }

    private fun renderView() {
        story_title.text = articleResponse.title
        val date = Date(articleResponse.time * 1000L)
        story_time.text = String.format(getString(R.string.story_time, date.toString()))
        story_descents.text = String.format(getString(R.string.story_descendants), articleResponse.descendants)
        story_type.text = String.format(getString(R.string.story_type), articleResponse.type)
        story_score.text = String.format(getString(R.string.story_score), articleResponse.score)
        story_by.text = String.format(getString(R.string.story_by), articleResponse.by)

    }

    fun setArticleInformation(argArticleResponse: ArticleResponse) {
        this.articleResponse = argArticleResponse
    }
}