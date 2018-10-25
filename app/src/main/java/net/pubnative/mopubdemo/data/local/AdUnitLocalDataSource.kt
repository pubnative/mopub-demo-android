package net.pubnative.mopubdemo.data.local

import android.content.Context
import net.pubnative.mopubdemo.data.AdUnitDataSource
import net.pubnative.mopubdemo.data.local.async.EditAdUnitTask
import net.pubnative.mopubdemo.data.local.async.FetchAllAdUnitsTask
import net.pubnative.mopubdemo.data.local.async.InsertAdUnitTask
import net.pubnative.mopubdemo.data.local.async.RemoveAdUnitTask
import net.pubnative.mopubdemo.models.AdUnit

class AdUnitLocalDataSource(private val context: Context) : AdUnitDataSource {
    private val database = AppDatabase.getInstance(context)

    override fun fetchAll(callback: AdUnitDataSource.FetchCallback) {
        FetchAllAdUnitsTask(database, object : FetchAllAdUnitsTask.FetchCallback {
            override fun onSuccess(items: List<AdUnit>) {
                callback.onSuccess(items)
            }

            override fun onFailure(throwable: Throwable) {
                callback.onError(throwable)
            }
        }).execute()
    }

    override fun add(adUnit: AdUnit, callback: AdUnitDataSource.AddCallback) {
        InsertAdUnitTask(database, object : InsertAdUnitTask.AddCallback {
            override fun onSuccess(responseCode: Long?) {
                callback.onSuccess()
            }

            override fun onFailure(throwable: Throwable) {
                callback.onError(throwable)
            }
        }).execute(adUnit)
    }

    override fun edit(adUnit: AdUnit) {
        EditAdUnitTask(database, object : EditAdUnitTask.EditCallback {
            override fun onFinished() {

            }
        }).execute(adUnit)
    }

    override fun remove(adUnit: AdUnit) {
        RemoveAdUnitTask(database, object : RemoveAdUnitTask.RemoveCallback {
            override fun onFinished() {

            }
        }).execute(adUnit)
    }
}