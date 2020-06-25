package com.example.smkccovid.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_data")
data class CountryDataModel (
    var country: String,
    var countryCode: String,
    var date: String,
    var newConfirmed: Int,
    var newDeaths: Int,
    var newRecovered: Int,
    var slug: String,
    var totalConfirmed: Int,
    var totalDeaths: Int,
    var totalRecovered: Int,
    @PrimaryKey var key: String
) {
    constructor() : this("","","",0,0,0,"",0,0,0, "")
}