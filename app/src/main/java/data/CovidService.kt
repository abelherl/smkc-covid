package data

import com.example.smkccovid.data.Summary
import retrofit2.Call
import retrofit2.http.GET

interface CovidService {
    @GET("summary")
    fun getGlobal(): Call<Summary>
}