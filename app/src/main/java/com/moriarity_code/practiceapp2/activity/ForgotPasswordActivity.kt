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

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var etMobileNumber: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnChangePassword: Button

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        title = "Forgot Password"


        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnChangePassword = findViewById(R.id.btnChangePassword)

        sharedPreferences = getSharedPreferences("Login Preferences", Context.MODE_PRIVATE)

        btnChangePassword.setOnClickListener {
            var mobileNumber = etMobileNumber.text.toString()
            var password = etPassword.text.toString()
            var confpassword = etConfirmPassword.text.toString()

            if (password == confpassword) {
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Password changed",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(
                    this@ForgotPasswordActivity,
                    LoginActivityCopy::class.java
                )
                sharedPreferences.edit().putString("Password", password).commit()
                val name = sharedPreferences.getString("Name", "Admin")
                intent.putExtra("Name", name)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Passwords does not match",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
