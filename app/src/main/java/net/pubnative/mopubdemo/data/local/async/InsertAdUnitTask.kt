package net.pubnative.mopubdemo.data.local.async

import android.os.AsyncTask
import android.util.Log
import net.pubnative.mopubdemo.data.local.AppDatabase
import net.pubnative.mopubdemo.models.AdUnit

class InsertAdUnitTask(private val database: AppDatabase?, private val callback: AddCallback?) :
    AsyncTask<AdUnit, Void, Long?>() {

    override fun doInBackground(vararg params: AdUnit?): Long? {
        val adUnit = params[0]

        var response: Long? = null

        if (adUnit != null) {
            try {
                val adUnitDao = database?.adUnitDao()
                response = adUnitDao?.insert(adUnit)
            } catch (exception: Exception) {
                Log.e(InsertAdUnitTask::class.java.simpleName, exception.message ?: "Error inserting ad unit")
            }
        }

        return response
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