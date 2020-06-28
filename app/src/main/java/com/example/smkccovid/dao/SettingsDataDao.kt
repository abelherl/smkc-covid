package com.example.smkccovid.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.smkccovid.model.SettingsDataModel

@Dao
interface SettingsDataDao {
    @Query("SELECT * from settings_data")

    fun getAllSettingsData(): LiveData<List<SettingsDataModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setting: SettingsDataModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(settings: List<SettingsDataModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(setting: SettingsDataModel)

    @Delete()
    suspend fun delete(setting: SettingsDataModel)

    @Query("DELETE FROM settings_data")
    suspend fun deleteAll()
}