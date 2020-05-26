package com.moriarity_code.practiceapp2.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.moriarity_code.practiceapp2.R


class LoginActivity : AppCompatActivity() {
    lateinit var etMobileNumber: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnForgotPassword: Button
    lateinit var btnRegisterYourself: Button

    var validMobileNumber: String? = null
    var validPassword: String? = null

    var name: String? = null
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = "Code.Moriarity"

        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)

        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            name = sharedPreferences.getString("Name", "Admin")
            Toast.makeText(
                this@LoginActivity,
                "Welcome $name",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(
                this@LoginActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnForgotPassword = findViewById(R.id.btnForgotPassword)
        btnRegisterYourself = findViewById(R.id.btnRegisterYourself)

        validMobileNumber = sharedPreferences.getString("Mobile Number", "0000000000")
        validPassword = sharedPreferences.getString("Password", "admin")

        btnLogin.setOnClickListener {

            val mobileNumber = etMobileNumber.text.toString()
            val password = etPassword.text.toString()
            name = sharedPreferences.getString("Name", "Admin")

            if (mobileNumber == validMobileNumber && password == validPassword) {
                savePreferences()
                val intent = Intent(
                    this@LoginActivity,
                    MainActivity::class.java
                )
                startActivity(intent)
                Toast.makeText(
                    this@LoginActivity,
                    "Welcome $name",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Invalid Credentials",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnForgotPassword.setOnClickListener {
            val intent = Intent(
                this@LoginActivity,
                ForgotPasswordActivity::class.java
            )
            startActivity(intent)
            Toast.makeText(
                this@LoginActivity,
                "Forgot Password",
                Toast.LENGTH_SHORT
            ).show()
        }
        btnRegisterYourself.setOnClickListener {
            val intent = Intent(
                this@LoginActivity,
                RegistrationActivity::class.java
            )
            startActivity(intent)
            Toast.makeText(
                this@LoginActivity,
                "Register",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun savePreferences() {
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
    }

    /* override fun onPause() {
         super.onPause()
         finish()
     }*/
}
