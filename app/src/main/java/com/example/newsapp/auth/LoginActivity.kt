package com.example.newsapp.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        Log.d("LoginActivity", "onCreate called - Login screen loaded")

        val mainLayout = findViewById<android.view.View>(R.id.main)
        if (mainLayout != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        } else {
            Log.e("LoginActivity", "Error: View with ID 'main' not found. Check activity_login.xml")
        }

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val signUpText = findViewById<TextView>(R.id.signUpText)
        val forgotPasswordText = findViewById<TextView>(R.id.forgotPasswordText)

        loginButton.setOnClickListener {
            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString().trim()

            if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userPassword)) {
                Toast.makeText(this, "Email and Password are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null) {
                            if (user.isEmailVerified) {
                                Log.d("LoginSuccess", "User logged in successfully: ${user.email}")
                                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                Log.e("LoginError", "User email not verified")
                                showVerificationPopup(user)
                            }
                        }
                    } else {
                        val errorMessage = task.exception?.localizedMessage ?: "Unknown error"
                        Log.e("LoginError", "Login Failed: $errorMessage", task.exception)
                        Toast.makeText(this, "Login Failed: $errorMessage", Toast.LENGTH_LONG).show()
                    }
                }
        }

        signUpText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        forgotPasswordText.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun showVerificationPopup(user: FirebaseUser) {
        AlertDialog.Builder(this)
            .setTitle("Email Not Verified")
            .setMessage("Your email is not verified. Please check your inbox and verify your email.")
            .setPositiveButton("OK") { _, _ -> }
            .setNegativeButton("Resend Email") { _, _ ->
                user.sendEmailVerification().addOnCompleteListener { emailTask ->
                    if (emailTask.isSuccessful) {
                        Toast.makeText(this, "Verification email sent!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to resend email.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setCancelable(false)
            .show()
    }
}
