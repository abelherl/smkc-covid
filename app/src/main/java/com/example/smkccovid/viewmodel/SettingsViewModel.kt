package com.example.smkccovid.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smkccovid.db.SMKCDatabase
import com.example.smkccovid.model.SettingsDataModel
import com.example.smkccovid.repo.SettingsDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class SettingsViewModel : ViewModel() {

    lateinit var repository: SettingsDataRepository

    lateinit var allSettingsDatas: LiveData<List<SettingsDataModel>>

    fun init(context: Context) {
        val settingsDataDao = SMKCDatabase.getDatabase(context).settingsDataDao()
        repository = SettingsDataRepository(settingsDataDao)
        allSettingsDatas = repository.allSettingsData
    }

    fun delete(settingsData: SettingsDataModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(settingsData)
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    fun insert(settings: SettingsDataModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(settings)
        Log.d("TAG", "Data Updated 2: " + allSettingsDatas)
    }

    fun update(settings: SettingsDataModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(settings)
        Log.d("TAG", "Data Updated: " + allSettingsDatas)
    }
}
