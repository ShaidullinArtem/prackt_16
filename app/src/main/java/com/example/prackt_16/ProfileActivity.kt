package com.example.prackt_16

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert
import android.widget.Button

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val login_button = findViewById<Button>(R.id.button_login);
        val register_button = findViewById<Button>(R.id.button_register);

        login_button.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
            finish()
        }

        register_button.setOnClickListener {
            val browser = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ects.ru"));
            startActivity(browser);
            }
        }
}