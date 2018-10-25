package net.pubnative.mopubdemo.data.local.async

import android.os.AsyncTask
import net.pubnative.mopubdemo.data.local.AppDatabase
import net.pubnative.mopubdemo.models.AdUnit

class FetchAllAdUnitsTask(private val database: AppDatabase?, private val callback: FetchCallback?) :
    AsyncTask<Void, Void, List<AdUnit>?>() {

    override fun doInBackground(vararg params: Void?): List<AdUnit>? {
        val adUnitDao = database?.adUnitDao()
        return adUnitDao?.getAdUnits()
    }

    override fun onPostExecute(result: List<AdUnit>?) {
        super.onPostExecute(result)
        if (result == null) {
            callback?.onFailure(Exception("An error has occurred fetching the items"))
        } else {
            callback?.onSuccess(result)
        }
    }

    interface FetchCallback {
        fun onSuccess(items: List<AdUnit>)
        fun onFailure(throwable: Throwable)
    }
}