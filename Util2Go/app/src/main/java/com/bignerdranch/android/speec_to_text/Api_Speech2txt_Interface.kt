package com.bignerdranch.android.speec_to_text
import retrofit2.Call
import retrofit2.http.*

interface Api_Speech2txt_Interface {
    @GET
    fun getData(@Url url:String):Call<Response_Speec2txt>
}