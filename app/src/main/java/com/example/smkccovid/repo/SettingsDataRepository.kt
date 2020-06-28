package com.example.smkccovid.repo

import androidx.lifecycle.LiveData
import com.example.smkccovid.dao.SettingsDataDao
import com.example.smkccovid.model.SettingsDataModel

class SettingsDataRepository(private val settingsDataDao: SettingsDataDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allSettingsData: LiveData<List<SettingsDataModel>> = settingsDataDao.getAllSettingsData()

    suspend fun insert(settingsData: SettingsDataModel) {
        settingsDataDao.insert(settingsData)
    }

    suspend fun insertAll(settingsDatas: List<SettingsDataModel>) {
        settingsDataDao.insertAll(settingsDatas)
    }

    suspend fun deleteAll() {
        settingsDataDao.deleteAll()
    }

    suspend fun update(settingsData: SettingsDataModel) {
        settingsDataDao.update(settingsData)
    }

    suspend fun delete(settingsData: SettingsDataModel) {
        settingsDataDao.delete(settingsData)
    }
}
