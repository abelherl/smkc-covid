package com.example.smkccovid.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smkccovid.data.Country
import com.example.smkccovid.R
import com.example.smkccovid.data.NewsItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.news_item.*
import util.getDate
import java.util.*
import kotlin.collections.ArrayList

class NewsAdapter(private val context: Context, private val items : ArrayList<NewsItem>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.news_item,
                parent,
                false
            )
        )
    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context, items.get(position))
    }
    class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(context: Context, item: NewsItem) {
            tv_news_title.text = item.title
            tv_news_time.text = getDate(context, Locale.getDefault().country, item.publishedAt)
            Glide.with(context).load(item.urlToImage).error(context.resources.getDrawable(R.drawable.ic_broken_image_black_24dp)).into(iv_news)
        }
    }
}