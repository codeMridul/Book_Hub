package com.moriarity_code.practiceapp2.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.moriarity_code.practiceapp2.R


class LoginActivityCopy : AppCompatActivity() {
    lateinit var etMobileNumber: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnForgotPassword: Button
    lateinit var btnRegisterYourself: Button


    var validMobileNumber = "7839955401"
    var validPassword = "mridul503"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_copy)

        var name: String? = null
        if (intent != null) {
            name = intent.getStringExtra("Name")
        }
        title = "Hello, $name"

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnForgotPassword = findViewById(R.id.btnForgotPassword)
        btnRegisterYourself = findViewById(R.id.btnRegisterYourself)

        btnLogin.setOnClickListener {
            val mobileNumber = etMobileNumber.text.toString()
            val password = etPassword.text.toString()

            if (mobileNumber == validMobileNumber && password == validPassword) {
                val intent = Intent(
                    this@LoginActivityCopy,
                    MainActivity::class.java
                )
                startActivity(intent)
                Toast.makeText(
                    this@LoginActivityCopy,
                    "Logged In",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@LoginActivityCopy,
                    "Invalid Credentials",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        btnForgotPassword.setOnClickListener {

            val intent = Intent(
                this@LoginActivityCopy,
                ForgotPasswordActivity::class.java
            )
            startActivity(intent)
            Toast.makeText(
                this@LoginActivityCopy,
                "Forgot Password",
                Toast.LENGTH_SHORT
            ).show()
        }
        btnRegisterYourself.setOnClickListener {
            val intent = Intent(
                this@LoginActivityCopy,
                RegistrationActivity::class.java
            )
            startActivity(intent)
            Toast.makeText(
                this@LoginActivityCopy,
                "Register",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
