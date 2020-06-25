package com.example.smkccovid.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.smkccovid.model.CountryDataModel

@Dao
interface CountryDataDao {
    @Query("SELECT * from country_data")

    fun getAllCountryData(): LiveData<List<CountryDataModel>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: CountryDataModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(country: List<CountryDataModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(country: CountryDataModel)

    @Delete()
    suspend fun delete(country: CountryDataModel)

    @Query("DELETE FROM country_data")
    suspend fun deleteAll()
}