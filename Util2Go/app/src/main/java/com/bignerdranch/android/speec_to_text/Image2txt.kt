package com.bignerdranch.android.speec_to_text

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Image2txt: AppCompatActivity() {
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