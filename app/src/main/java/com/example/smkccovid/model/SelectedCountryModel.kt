package com.example.smkccovid.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_countries")
data class SelectedCountryModel (
    var country: String,
    @PrimaryKey
    var countryCode: String,
    var date: String,
    var newConfirmed: Int,
    var newDeaths: Int,
    var newRecovered: Int,
    var slug: String,
    var totalConfirmed: Int,
    var totalDeaths: Int,
    var totalRecovered: Int
) {
    constructor() : this("","","",0,0,0,"",0,0,0)
}