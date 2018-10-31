package net.pubnative.mopubdemo.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ad_unit")
data class AdUnit(
    @PrimaryKey val name: String,
    val adUnitId: String,
    val adSize: Int, val defaultUnit: Boolean = false
) : Parcelable {
    private constructor(parcel: Parcel?) : this(
        parcel?.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt() == 1
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(adUnitId)
        dest?.writeInt(adSize)
        dest?.writeInt(if (defaultUnit) 1 else 0)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<AdUnit> {
            override fun createFromParcel(source: Parcel?) = AdUnit(source)
            override fun newArray(size: Int) = arrayOfNulls<AdUnit>(size)
        }
    }
}