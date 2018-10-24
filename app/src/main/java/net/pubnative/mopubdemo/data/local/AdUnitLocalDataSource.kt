package net.pubnative.mopubdemo.data.local

import android.content.Context
import net.pubnative.mopubdemo.data.AdUnitDataSource
import net.pubnative.mopubdemo.models.AdUnit

class AdUnitLocalDataSource(private val context: Context) : AdUnitDataSource {

    override fun fetchAll(callback: AdUnitDataSource.Callback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(adUnit: AdUnit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun edit(adUnit: AdUnit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(adUnit: AdUnit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}