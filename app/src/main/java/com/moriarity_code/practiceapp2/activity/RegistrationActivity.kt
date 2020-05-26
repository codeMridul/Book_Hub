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

class RegistrationActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etMobileNumber: EditText
    lateinit var etEmailId: EditText
    lateinit var etPassword: EditText
    lateinit var etConfirmPassword: EditText
    lateinit var btnRegister: Button

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        title = "Register"

        sharedPreferences = getSharedPreferences("Login Prefenences", Context.MODE_PRIVATE)

        etName = findViewById(R.id.etName)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etEmailId = findViewById(R.id.etEmailId)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            var name = etName.text.toString()
            var mobileNumber = etMobileNumber.text.toString()
            var emailId = etEmailId.text.toString()
            var password = etPassword.text.toString()
            var confPassword = etConfirmPassword.text.toString()
            if (password == confPassword) {
                sharedPreferences.edit().clear().apply()
                savePreferences(name, mobileNumber, emailId, password)
                Toast.makeText(this@RegistrationActivity, "Welcome, $name", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Password does not match with the confirm Password",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    fun savePreferences(name: String, mob: String, email: String, password: String) {
        sharedPreferences.edit().putString("Name", name).commit()
        sharedPreferences.edit().putString("Mobile Number", mob).commit()
        sharedPreferences.edit().putString("Email Id", email).commit()
        sharedPreferences.edit().putString("Password", password).commit()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
