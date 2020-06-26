package com.example.smkccovid.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smkccovid.db.SMKCDatabase
import com.example.smkccovid.model.CountryDataModel
import com.example.smkccovid.repo.CountryDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    lateinit var repository: CountryDataRepository

    lateinit var allCountryDatas: LiveData<List<CountryDataModel>>

    fun init(context: Context) {
        val countryDataDao = SMKCDatabase.getDatabase(context).countryDataDao()
        repository = CountryDataRepository(countryDataDao)
        allCountryDatas = repository.allCountryData
    }

    fun delete(countryData: CountryDataModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(countryData)
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertAll(countryDatas: List<CountryDataModel>) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TAG", "All Data Updated 1: " + repository.allCountryData.value)
//        repository.deleteAll()
        repository.insertAll(countryDatas)
        Log.d("TAG", "All Data Updated 2: " + repository.allCountryData.value)
    }
}
