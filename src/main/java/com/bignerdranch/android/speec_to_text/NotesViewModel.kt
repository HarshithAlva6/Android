package com.bignerdranch.android.speec_to_text

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel


class NotesViewModel: ViewModel() {
    private var image: Drawable? = null
    private var text: String? = null

    fun getImage(): Drawable? {
        return image
    }

    fun setImage(image: Drawable) {
        this.image = image
    }
    fun getText(): String? {
        return text
    }

    fun setText(text: String) {
        this.text = text
    }
}