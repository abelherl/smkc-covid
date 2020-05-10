package data

import com.example.smkccovid.GlobalSummary
import com.example.smkccovid.Summary
import retrofit2.Call
import retrofit2.http.GET

interface CovidService {
    @GET("summary")
    fun getGlobal(): Call<Summary>
}