package com.bignerdranch.android.speec_to_text

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MenuActivity: AppCompatActivity() {
    private lateinit var Qrcode: ImageButton
    private lateinit var Accelerometer: ImageButton
    private lateinit var Speec2txt: ImageButton
    private lateinit var Pdfscan: ImageButton
    private lateinit var Signout: Button
    private lateinit var Textvw: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)

        Speec2txt=findViewById(R.id.MA_Speech2txt)
        Accelerometer=findViewById(R.id.MA_accelerometer)
        Pdfscan=findViewById(R.id.MA_pdfscan)
        Qrcode=findViewById(R.id.MA_qrcode)
        Signout=findViewById(R.id.MA_signout)
        Textvw=findViewById(R.id.MA_TextView1)
        Textvw.setText("Welcome Testuser")
        Speec2txt.setOnClickListener { view: View ->
            val intent = Intent(this, Speech2txt::class.java)
            startActivity(intent)
        }
        Signout.setOnClickListener { view: View->
            signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        Accelerometer.setOnClickListener{view: View->
            val intent = Intent(this, Acclerometer::class.java)
            startActivity(intent)
            }
        Pdfscan.setOnClickListener{ view: View->
            val intent = Intent(this, Image2txt::class.java)
            startActivity(intent)
        }

    }
    private fun signOut() {
        // [START auth_sign_out]
        Firebase.auth.signOut()
        Toast.makeText(this, "Logged out " ,
            Toast.LENGTH_SHORT).show()
        // [END auth_sign_out]
    }
    public override fun onStart() {
        super.onStart()
    }
}