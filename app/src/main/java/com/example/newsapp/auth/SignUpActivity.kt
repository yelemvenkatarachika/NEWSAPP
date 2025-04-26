package com.example.newsapp.auth

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        val fullName = findViewById<EditText>(R.id.fullName)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val confirmPassword = findViewById<EditText>(R.id.confirmPassword)
        val signUpButton = findViewById<Button>(R.id.signupButton)
        val loginText = findViewById<TextView>(R.id.loginText)

        signUpButton.setOnClickListener {
            val userFullName = fullName.text.toString().trim()
            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString().trim()
            val userConfirmPassword = confirmPassword.text.toString().trim()

            if (TextUtils.isEmpty(userFullName)) {
                fullName.error = "Full Name is required"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                email.error = "Enter a valid Google email"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(userPassword) || userPassword.length < 6) {
                password.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            if (userPassword != userConfirmPassword) {
                confirmPassword.error = "Passwords do not match"
                return@setOnClickListener
            }

            // Create user in Firebase Authentication
            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sendEmailVerification()
                    } else {
                        Toast.makeText(
                            this,
                            "Signup Failed: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun sendEmailVerification() {
        val user: FirebaseUser? = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
            if (emailTask.isSuccessful) {
                showVerificationPopup()
            } else {
                Toast.makeText(
                    this,
                    "Failed to send verification email.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showVerificationPopup() {
        AlertDialog.Builder(this)
            .setTitle("Verify Your Email")
            .setMessage("A verification email has been sent. Please check your inbox and verify your email before logging in.")
            .setPositiveButton("OK") { _, _ ->
                auth.signOut() // Sign out to prevent unverified login
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .setNegativeButton("Resend Email") { _, _ ->
                sendEmailVerification()
            }
            .setCancelable(false)
            .show()
    }
}
