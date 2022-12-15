package com.example.finalapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val levelMeterBtn = findViewById(R.id.levelMeterBtn) as ImageButton
        val qrCodeBtn = findViewById(R.id.qrCodeBtn) as ImageButton

        // goes to levelMeter Application
        levelMeterBtn.setOnClickListener {
            Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,LevelMeterActivity::class.java)
            startActivity(intent)
        }
    }
}