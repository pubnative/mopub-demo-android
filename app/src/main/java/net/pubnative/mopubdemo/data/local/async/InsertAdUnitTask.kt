package net.pubnative.mopubdemo.data.local.async

import android.os.AsyncTask
import net.pubnative.mopubdemo.data.local.AppDatabase
import net.pubnative.mopubdemo.models.AdUnit

class InsertAdUnitTask(private val database: AppDatabase?, private val callback: AddCallback?) :
    AsyncTask<AdUnit, Void, Long?>() {

    override fun doInBackground(vararg params: AdUnit?): Long? {
        val adUnit = params[0]
        return if (adUnit != null) {
            val adUnitDao = database?.adUnitDao()
            adUnitDao?.insert(adUnit)
        } else {
            0
        }

    }

    override fun onPostExecute(result: Long?) {
        super.onPostExecute(result)
        if (result == null) {
            callback?.onFailure(Exception("Error inserting the ad unit"))
        } else {
            callback?.onSuccess(result)
        }
    }

    interface AddCallback {
        fun onSuccess(responseCode: Long?)
        fun onFailure(throwable: Throwable)
    }
}