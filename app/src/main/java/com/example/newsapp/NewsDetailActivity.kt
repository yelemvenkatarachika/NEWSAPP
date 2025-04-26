package com.example.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var countryText: TextView
    private lateinit var languageText: TextView
    private lateinit var genreText: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_news_detail)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, windowInsets ->
            val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            windowInsets
        }

        // Initialize your TextViews
        countryText = findViewById(R.id.countryText)
        languageText = findViewById(R.id.languageText)
        genreText = findViewById(R.id.genreText)

        // Setup dropdown click listeners
        setupDropdown(countryText, listOf("India", "USA", "UK", "Australia"))
        setupDropdown(languageText, listOf("English", "Hindi", "Spanish", "French"))
        setupDropdown(genreText, listOf("Politics", "Sports", "Technology", "Entertainment"))
    }

    private fun setupDropdown(textView: TextView, options: List<String>) {
        textView.setOnClickListener {
            val popupMenu = PopupMenu(this, textView)
            options.forEach { option ->
                popupMenu.menu.add(option)
            }
            popupMenu.setOnMenuItemClickListener { menuItem ->
                textView.text = menuItem.title
                true
            }
            popupMenu.show()
        }
    }
}
