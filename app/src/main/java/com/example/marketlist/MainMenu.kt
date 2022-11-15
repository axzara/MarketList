package com.example.marketlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val btnCreateProduct = findViewById<Button>(R.id.button3)


        btnCreateProduct.setOnClickListener {
            val intent = Intent(this, CreateProduct::class.java)
            startActivity(intent)
        }
    }
}