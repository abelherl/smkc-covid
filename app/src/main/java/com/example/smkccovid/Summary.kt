package com.example.smkccovid


import com.google.gson.annotations.SerializedName

data class Summary(
    @SerializedName("Countries")
    val countries: List<CountrySummary>,
    @SerializedName("Date")
    val date: String,
    @SerializedName("GlobalSummary")
    val globalSummary: GlobalSummary
)