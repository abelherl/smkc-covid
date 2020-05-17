package data

import com.example.smkccovid.data.NewCountryItem
import com.example.smkccovid.data.CountryTotal
import com.example.smkccovid.data.NewsItem
import com.example.smkccovid.data.Summary
import com.example.smkccovid.data.WorldWeeklyItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CovidService {
    @GET("summary")
    fun getGlobal(): Call<Summary>

    @GET("countries")
    fun getCountries(): Call<List<NewCountryItem>>

    @GET("world")
    fun getWeeklyWorld(): Call<List<WorldWeeklyItem>>

    @GET("dayone/country/{country}")
    fun getCountry(@Path("country") country: String): Call<List<CountryTotal>>

    @GET("v2/top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("q") q: String
    ): Call<List<NewsItem>>
}