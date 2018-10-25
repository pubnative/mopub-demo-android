package net.pubnative.mopubdemo.data.local

import androidx.room.*
import net.pubnative.mopubdemo.models.AdUnit

@Dao
interface AdUnitDao {
    @Query("SELECT * FROM ad_unit")
    fun getAdUnits(): List<AdUnit>

    @Query("SELECT * FROM ad_unit WHERE name = :name")
    fun getAdUnit(name: String): AdUnit

    @Insert
    fun insert(adUnit: AdUnit): Long

    @Update
    fun edit(adUnit: AdUnit)

    @Delete
    fun remove(adUnit: AdUnit)
}