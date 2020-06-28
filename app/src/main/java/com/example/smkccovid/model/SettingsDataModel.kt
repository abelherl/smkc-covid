package com.example.smkccovid.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_data")
data class SettingsDataModel (
    @PrimaryKey
    var id: String,
    var language: String,
    var notification: Boolean,
    var global: Boolean,
    var country: Boolean
) {
    constructor() : this("","",true,true, true)
}