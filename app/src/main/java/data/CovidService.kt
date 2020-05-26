package data

import com.example.smkccovid.data.*
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
        @Query("q") q: String,
        @Query("pageSize") pageSize: Int
    ): Call<NewsParent>
}