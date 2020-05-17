package com.example.smkccovid.data


import com.google.gson.annotations.SerializedName

data class NewCountryItem(
    @SerializedName("Country")
    val country: String,
    @SerializedName("ISO2")
    val iSO2: String,
    @SerializedName("Slug")
    val slug: String
)