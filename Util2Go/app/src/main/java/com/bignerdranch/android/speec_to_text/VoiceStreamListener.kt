package com.bignerdranch.android.speec_to_text

interface VoiceStreamListener {
    fun onVoiceStreaming(data: ByteArray?, size: Int)
    }
