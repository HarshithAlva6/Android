package com.example.imagetotext

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var addImgButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addImgButton = findViewById(R.id.add_image)
        addImgButton.setOnClickListener { view: View ->
            val intent = Intent(this, ImageToText::class.java)
            startActivity(intent)
        }
    }
}