package com.test.hackernews.ux.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.test.hackernews.R
import com.test.hackernews.model.ArticleResponse
import com.test.hackernews.network.ApiInterface
import com.test.hackernews.network.getClient
import com.test.hackernews.ux.ActionListener
import com.test.hackernews.ux.Communicator
import com.test.hackernews.ux.MainActivity
import com.test.hackernews.ux.adapter.NewsAdapter
import kotlinx.android.synthetic.main.fragment_topstories.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class TopStoriesListFragment : Fragment(), ActionListener {
    private lateinit var communicator: Communicator
    private lateinit var newsAdapter: NewsAdapter

    override fun onAction(action: String, data: Any?) {
        when (action) {
            STORY_ITEM_SELECTED -> {
                openArticle(data as ArticleResponse)
                true
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_topstories, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        communicator = requireActivity() as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        fetchTopNews()
    }

    private fun initViews() {
        newsAdapter = NewsAdapter(requireContext(), this)
        rec_topstoreis.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        rec_topstoreis.itemAnimator = DefaultItemAnimator()
    }

    /**
     * Method to fetch top 20 stories
     */
    private fun fetchTopNews() {
        val storiesList = ArrayList<ArticleResponse>()
        val apiInterface = getClient()?.create(ApiInterface::class.java)
        apiInterface?.getTopStories()?.enqueue(object : Callback<List<Int>> {
            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                val topStories = response.body()
                for (i in 0..20) {
                    topStories?.get(i)?.let {
                        apiInterface.getArticle(it).enqueue(object : Callback<ArticleResponse> {
                            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                                response.body()?.let { it1 -> storiesList.add(it1) }
                                rendersNewArticles(storiesList)
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

            }

            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                Toast.makeText(requireContext(), getString(R.string.common_network_error), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun openArticle(article: ArticleResponse) {
        if (article.url.isNotEmpty()) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
        } else {
            val articleDetailsFragment = ArticleDetailsFragment()
            articleDetailsFragment.setArticleInformation(article)
            communicator.showFragment(articleDetailsFragment)
        }
    }

    /**
     * Rendering RecyclerView with data
     */
    private fun rendersNewArticles(list: ArrayList<ArticleResponse>) {
        newsAdapter.setData(list)
        newsAdapter.notifyDataSetChanged()
    }

    companion object {
        const val STORY_ITEM_SELECTED = "storySelected"
    }
}