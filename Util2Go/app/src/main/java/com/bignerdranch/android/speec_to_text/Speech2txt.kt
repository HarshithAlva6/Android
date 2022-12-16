package com.bignerdranch.android.speec_to_text


import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Build
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import android.widget.*

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

const val FINAL_URL="https://firebasestorage.googleapis.com/v0/b/cpsc411-finalproject.appspot.com/o/"
class Speech2txt : AppCompatActivity() {

    // on below line we are creating variables
    // for text view and image view
    lateinit var outputTV: TextView
    lateinit var micIV: ImageView
    val storage = Firebase.storage
    var storageRef = storage.reference
    private lateinit var speaker: ImageButton
    // on below line we are creating a constant value
    private val REQUEST_CODE_SPEECH_INPUT = 1
    private lateinit var backbtn: Button
    private lateinit var savebtn: Button

    //private lateinit var savebtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.speech2txt)

        // initializing variables of list view with their ids.
        outputTV = findViewById(R.id.idTVOutput)
        micIV = findViewById(R.id.idIVMic)

        // on below line we are adding on click
        // listener for mic image view.
        micIV.setOnClickListener {
            // on below line we are calling speech recognizer intent.
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            // on below line we are passing language model
            // and model free form in our intent
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            // on below line we are passing our
            // language as a default language.
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            // on below line we are specifying a prompt
            // message as speak to text on below line.
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

            // on below line we are specifying a try catch block.
            // in this block we are calling a start activity
            // for result method and passing our result code.
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                // on below line we are displaying error message in toast
                Toast
                    .makeText(
                        this, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }
        speaker=findViewById(R.id.Speaker)
        speaker.setOnClickListener {
            val text = outputTV.text.toString().trim()
            if (text.isNotEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1")
                } else {
                    textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null)
                }
            } else {
                Toast.makeText(this, "Text cannot be empty", Toast.LENGTH_LONG).show()
            }
        }
        backbtn=findViewById(R.id.backbtn)
        backbtn.setOnClickListener { view: View ->
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        savebtn=findViewById(R.id.savebtn)
        savebtn.setOnClickListener {
        loadthetext()
        }

    }

    // on below line we are calling on activity result method.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // in this method we are checking request
        // code with our result code.
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            // on below line we are checking if result code is ok
            if (resultCode == RESULT_OK && data != null) {

                // in that case we are extracting the
                // data from our array list
                val res: ArrayList<String> =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                // on below line we are setting data
                // to our output text view.
                outputTV.setText(
                    Objects.requireNonNull(res)[0]
                )

            }
        }
    }
    companion object {
        private const val REQUEST_CODE_STT = 1
    }
private fun loadthetext(){
    var gson = GsonBuilder().setLenient().create()
    val appurl= FINAL_URL+"myObject?alt=media&token=f8a708af-b162-4e56-ad02-4d797ddf26c2"
    Log.i(TAG,"Interested *************************************")
    val retrofitBuilder= Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(
        FINAL_URL).build().create(Api_Speech2txt_Interface::class.java)
    val retrofit = retrofitBuilder.getData(appurl)
    Log.i(TAG, "Request sent")
    retrofit.enqueue(object :Callback<Response_Speec2txt>{
        override fun onResponse(call:Call<Response_Speec2txt>,response: Response<Response_Speec2txt>){
            val responsebody=response.body()
            Log.i(TAG, "Response came")
            Log.i(TAG,responsebody.toString())
            outputTV.setText(responsebody.toString())

        }

        override fun onFailure(call: Call<Response_Speec2txt>?, t: Throwable?) {
            if (t != null) {
                Log.i(ContentValues.TAG,"On failure" + t.message)
            }
        }
    }
    )
}

    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(this,
            TextToSpeech.OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeechEngine.language = Locale.US
                }
            })
    }
}