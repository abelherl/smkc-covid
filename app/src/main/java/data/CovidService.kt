package data

import com.example.smkccovid.NewCountry
import com.example.smkccovid.NewCountryItem
import com.example.smkccovid.data.Summary
import retrofit2.Call
import retrofit2.http.GET

interface CovidService {
    @GET("summary")
    fun getGlobal(): Call<Summary>

    @GET("countries")
    fun getCountries(): Call<List<NewCountryItem>>
}