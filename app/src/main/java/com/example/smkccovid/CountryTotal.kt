package com.example.smkccovid


import com.google.gson.annotations.SerializedName

data class CountryTotal(
    @SerializedName("Active")
    val active: Int,
    @SerializedName("City")
    val city: String,
    @SerializedName("CityCode")
    val cityCode: String,
    @SerializedName("Confirmed")
    val confirmed: Int,
    @SerializedName("Country")
    val country: String,
    @SerializedName("CountryCode")
    val countryCode: String,
    @SerializedName("Date")
    val date: String,
    @SerializedName("Deaths")
    val deaths: Int,
    @SerializedName("Lat")
    val lat: String,
    @SerializedName("Lon")
    val lon: String,
    @SerializedName("Province")
    val province: String,
    @SerializedName("Recovered")
    val recovered: Int
)