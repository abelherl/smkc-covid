package com.example.smkccovid.data


import com.google.gson.annotations.SerializedName

data class NewsParent(
    @SerializedName("articles")
    val articles: List<NewsItem>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)