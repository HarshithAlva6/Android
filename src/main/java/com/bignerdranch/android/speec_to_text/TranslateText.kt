package com.bignerdranch.android.speec_to_text

import android.content.ContentValues.TAG
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.Notes


class TranslateText : AppCompatActivity() {
    private lateinit var generateButton : Button
    private lateinit var cacheButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)

            Amplify.configure(applicationContext)

            Log.i("MyAmplifyApp", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error)
        }
        setContentView(R.layout.activity_translate_text)
        generateButton = findViewById(R.id.genButton)
        cacheButton = findViewById(R.id.cacheButton)
        val tv = findViewById<View>(R.id.textView) as TextView
        tv.text = intent.extras!!.getString("Text")
        if(tv.text == null){

        }
        val NoteNew = Notes.builder()
            .description(tv.text.toString())
            .build()
        generateButton.setOnClickListener { view: View ->
            generatePDF(tv)
        }
        cacheButton.setOnClickListener { view: View ->
            Amplify.DataStore.query(Notes::class.java,
                { matches ->
                    while (matches.hasNext()) {
                        val note = matches.next()
                        Amplify.DataStore.delete(note,
                            { Log.i("MyAmplifyApp", "Deleted a post.") },
                            { Log.e("MyAmplifyApp", "Delete failed.", it) }
                        )
                    }
                },
                { Log.e("MyAmplifyApp", "Query failed.", it) }
            )
        }

        Amplify.DataStore.save(NoteNew,
            { Log.i("MyAmplifyApp", "Created a new post successfully") },
            { Log.e("MyAmplifyApp", "Error creating post") }
        )
        Amplify.DataStore.query(Notes::class.java,
            { matches ->
                while (matches.hasNext()) {
                    val eachNote = matches.next()
                    Log.i("MyAmplifyApp", "Successful query, found posts: ${eachNote.description}")
                }
            },
            { Log.e("MyAmplifyApp",  "Error retrieving posts", it) }
        )
    }
    private fun generatePDF(textView: TextView) {
        val pdfDocument = PdfDocument()
        val title = Paint()
        val mypageInfo = PageInfo.Builder(1120, 792, 1).create()
        val myPage = pdfDocument.startPage(mypageInfo)
        val canvas: Canvas = myPage.canvas
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        title.setTextSize(15F)
        title.setColor(ContextCompat.getColor(this, R.color.purple_200))
        val x = 209
        var y = 100
        for (line in textView.getText().split("\n")) {
            canvas.drawText(line, x.toFloat(), y.toFloat(), title)
            y += title.descent().toInt() - title.ascent().toInt()
        }
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.setColor(ContextCompat.getColor(this, R.color.purple_200))
        title.setTextSize(15F)
        title.setTextAlign(Paint.Align.CENTER)
        pdfDocument.finishPage(myPage)
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "convert.pdf")
        Log.v(TAG, "File"+file)
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Log.v(TAG, "Filezz"+file)
            Toast.makeText(
                this,
                "PDF file generated successfully.",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        pdfDocument.close()
    }
}