package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.model.NewsItem

class NewsAdapter(
    private val newsList: List<NewsItem>,
    private val onClick: (NewsItem) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    // ViewHolder class holds views for one news item
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val newsDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val newsImage: ImageView = itemView.findViewById(R.id.imageViewNews)
    }

    // Inflates the item layout (items_news.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_news, parent, false)
        return NewsViewHolder(view)
    }

    // Binds data to each item view
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        with(holder) {
            newsTitle.text = newsItem.title
            newsDescription.text = newsItem.description
            newsImage.setImageResource(newsItem.imageRes)
            itemView.setOnClickListener { onClick(newsItem) }
        }
    }

    // Returns total number of items
    override fun getItemCount(): Int = newsList.size
}
