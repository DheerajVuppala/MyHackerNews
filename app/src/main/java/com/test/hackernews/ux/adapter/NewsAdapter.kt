package com.test.hackernews.ux.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.hackernews.R
import com.test.hackernews.model.ArticleResponse
import com.test.hackernews.ux.ActionListener
import com.test.hackernews.ux.fragments.TopStoriesListFragment.Companion.STORY_ITEM_SELECTED
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter(private val requireContext: Context, private val wrapActionListener: ActionListener) :
    RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    private var articlesList: ArrayList<ArticleResponse>? = null
    fun setData(arrayList: ArrayList<ArticleResponse>?) {
        this.articlesList = arrayList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(requireContext).inflate(R.layout.news_item, parent, false)
        return NewsHolder(view, wrapActionListener)
    }

    override fun getItemCount(): Int {
        return articlesList?.size ?: 0
    }

    override fun onBindViewHolder(newsHolder: NewsHolder, position: Int) {
        articlesList?.get(position)?.let { newsHolder.setData(it) }
    }

    /**
     * View Holder, to set click listener and form data view
     */
    class NewsHolder(itemView: View, wrapActionListener: ActionListener) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val selectedArticle = it.tag as ArticleResponse
                wrapActionListener.onAction(STORY_ITEM_SELECTED, selectedArticle)
            }
        }

        fun setData(article: ArticleResponse) {
            itemView.tag = article
            itemView.title_news.text = article.title
        }
    }
}