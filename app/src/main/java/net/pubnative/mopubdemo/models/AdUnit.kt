package net.pubnative.mopubdemo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ad_unit")
data class AdUnit(
    @PrimaryKey val name: String,
    val adUnitId: String,
    val adSize: Int
)