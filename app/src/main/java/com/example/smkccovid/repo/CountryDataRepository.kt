package com.example.smkccovid.repo

import androidx.lifecycle.LiveData
import com.example.smkccovid.dao.CountryDataDao
import com.example.smkccovid.model.CountryDataModel

class CountryDataRepository(private val countryDataDao: CountryDataDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allCountryData: LiveData<List<CountryDataModel>> = countryDataDao.getAllCountryData()

    suspend fun insert(countryData: CountryDataModel) {
        countryDataDao.insert(countryData)
    }

    suspend fun insertAll(countryDatas: List<CountryDataModel>) {
        countryDataDao.insertAll(countryDatas)
    }

    suspend fun deleteAll() {
        countryDataDao.deleteAll()
    }

    suspend fun update(countryData: CountryDataModel) {
        countryDataDao.update(countryData)
    }

    suspend fun delete(countryData: CountryDataModel) {
        countryDataDao.delete(countryData)
    }
}
