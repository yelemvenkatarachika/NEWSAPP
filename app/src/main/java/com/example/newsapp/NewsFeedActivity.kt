package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.model.NewsItem

class NewsFeedActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)

        // Initialize Spinners
        setupSpinners()

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewNews)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Sample Data
        val newsList = listOf(
            NewsItem("Politics Update", "Latest political events happening around the world.", R.drawable.political_nes),
            NewsItem("Sports Highlights", "Top sports highlights of the week.", R.drawable.sport_news),
            NewsItem("Tech Innovations", "Newest trends in the tech industry.", R.drawable.tech_news)
        )

        // Set Adapter
        newsAdapter = NewsAdapter(newsList) { newsItem ->
            val intent = Intent(this, NewsDetailActivity::class.java)
            intent.putExtra("title", newsItem.title)
            intent.putExtra("description", newsItem.description)
            startActivity(intent)
        }

        recyclerView.adapter = newsAdapter
    }

    private fun setupSpinners() {
        val categories = arrayOf("Politics", "Sports", "Technology", "Health", "Business")
        val countries = arrayOf("USA", "India", "UK", "Germany", "France")
        val newspapers = arrayOf("BBC", "CNN", "The Guardian", "Reuters")

        setupSpinner(R.id.spinnerCategory, categories)
        setupSpinner(R.id.spinnerCountry, countries)
        setupSpinner(R.id.spinnerNewspaper, newspapers)
    }

    private fun setupSpinner(spinnerId: Int, items: Array<String>) {
        val spinner: Spinner = findViewById(spinnerId)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                Toast.makeText(applicationContext, "Selected: ${items[position]}", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
