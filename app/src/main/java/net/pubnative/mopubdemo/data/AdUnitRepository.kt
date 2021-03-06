package net.pubnative.mopubdemo.data

import android.content.Context
import net.pubnative.mopubdemo.data.local.AdUnitLocalDataSource
import net.pubnative.mopubdemo.models.AdUnit

class AdUnitRepository(private val context: Context) : AdUnitDataSource {
    private val localDataSource: AdUnitDataSource

    init {
        localDataSource = AdUnitLocalDataSource(context)
    }

    override fun fetchAll(callback: AdUnitDataSource.FetchCallback) {
        localDataSource.fetchAll(callback)
    }

    override fun add(adUnit: AdUnit, callback: AdUnitDataSource.AddCallback) {
        localDataSource.add(adUnit, callback)
    }

    override fun edit(adUnit: AdUnit) {
        localDataSource.edit(adUnit)
    }

    override fun remove(adUnit: AdUnit) {
        localDataSource.remove(adUnit)
    }
}