package com.bignerdranch.android.speec_to_text
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions



class ImageToText : AppCompatActivity() {
    var fullText = ""
    private fun findText(image: InputImage) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                for (block in visionText.textBlocks) {
                    for (line in block.lines) {
                        val lineText = line.text
                        fullText += lineText + "\n"
                        Log.v(TAG, "The line" + lineText)
                        Log.v(TAG, "The full text" + fullText)
                        for (element in line.elements) {
                            val elementText = element.text
                            Log.v(TAG, "The Text" + elementText)
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
            }
    }

    private fun getTextRecognizer(): TextRecognizer {
        return TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }
    private lateinit var firstButton : Button
    private lateinit var secondButton: Button
    private lateinit var imageView : ImageView
    private lateinit var viewModel: NotesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_to_text)
        firstButton = findViewById(R.id.firstButton)
        secondButton = findViewById(R.id.secButton)
        imageView = findViewById(R.id.idImage)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        if (viewModel.getImage() != null) {
            imageView.setImageDrawable(viewModel.getImage());
        }
        if(viewModel.getText() != null) {
            fullText = viewModel.getText().toString()
        }
        firstButton.setOnClickListener { view: View ->
            imageView.setImageResource(R.drawable.text);
            val drawable = imageView.drawable as BitmapDrawable
            val bitmap = drawable.bitmap
            val image = InputImage.fromBitmap(bitmap, 0)
            findText(image)
            viewModel.setText(fullText)
        }
        secondButton.setOnClickListener { view: View ->
            val intent= Intent(this, TranslateText::class.java)
            intent.putExtra("Text", fullText)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (viewModel.getImage() != null) {
            imageView.setImageDrawable(viewModel.getImage());
            viewModel.setImage(imageView.drawable)
        } else {
            viewModel.setImage(imageView.drawable)
        }
        viewModel.setText(fullText)
    }
    override fun onResume() {
        super.onResume()
        if (viewModel.getImage() != null) {
            imageView.setImageDrawable(viewModel.getImage());
        }
        viewModel.setText(fullText)
    }
}