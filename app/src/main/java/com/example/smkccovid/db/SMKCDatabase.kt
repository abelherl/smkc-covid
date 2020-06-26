package com.example.smkccovid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smkccovid.dao.CountryDataDao
import com.example.smkccovid.model.CountryDataModel

@Database(entities = arrayOf(CountryDataModel::class), version = 3, exportSchema = false)
abstract class SMKCDatabase : RoomDatabase() {

    abstract fun countryDataDao(): CountryDataDao

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
