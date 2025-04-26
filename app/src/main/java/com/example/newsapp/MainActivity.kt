package com.example.newsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.auth.ForgotPasswordActivity
import com.example.newsapp.auth.SignUpActivity
import com.example.newsapp.NewsFeedActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ✅ Add Dynamic Shortcut (for Android 7.1+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val shortcutManager = getSystemService(ShortcutManager::class.java)

            val shortcut = ShortcutInfo.Builder(this, "news_shortcut_id")
                .setShortLabel("News App")
                .setLongLabel("Open News App")
                .setIcon(Icon.createWithResource(this, R.drawable.logo))
                .setIntent(Intent(this, MainActivity::class.java).apply {
                    action = Intent.ACTION_MAIN
                })
                .build()

            shortcutManager?.dynamicShortcuts = listOf(shortcut)
        }

        // 🔍 Find Views
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpText = findViewById<TextView>(R.id.signUpText)
        val forgotPasswordText = findViewById<TextView>(R.id.forgotPasswordText)

        // 🔐 Login Button Click
        loginButton.setOnClickListener {
            val user = username.text.toString().trim()
            val pass = password.text.toString().trim()

            // Simple hardcoded check - Replace with real login logic later
            if (user.isNotEmpty() && pass == "1234") {
                val intent = Intent(this, NewsFeedActivity::class.java).apply {
                    putExtra("username", user)
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
            }
        }

        // ➕ Sign Up Click
        signUpText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // ❓ Forgot Password Click
        forgotPasswordText.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}
