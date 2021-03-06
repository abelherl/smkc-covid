package com.example.smkccovid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smkccovid.dao.CountryDataDao
import com.example.smkccovid.dao.SettingsDataDao
import com.example.smkccovid.model.CountryDataModel
import com.example.smkccovid.model.SettingsDataModel

@Database(entities = arrayOf(CountryDataModel::class, SettingsDataModel::class), version = 3, exportSchema = false)
abstract class SMKCDatabase : RoomDatabase() {

    abstract fun countryDataDao(): CountryDataDao
    abstract fun settingsDataDao(): SettingsDataDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SMKCDatabase? = null

        fun getDatabase(context: Context): SMKCDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SMKCDatabase::class.java,
                    "smkc_covid_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
