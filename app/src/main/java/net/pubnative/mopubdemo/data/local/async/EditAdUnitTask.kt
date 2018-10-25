package net.pubnative.mopubdemo.data.local.async

import android.os.AsyncTask
import net.pubnative.mopubdemo.data.local.AppDatabase
import net.pubnative.mopubdemo.models.AdUnit

class EditAdUnitTask(private val database: AppDatabase?, private val callback: EditCallback?) :
    AsyncTask<AdUnit, Void, Long?>() {

    override fun doInBackground(vararg params: AdUnit?): Long? {
        val adUnit = params[0]
        if (adUnit != null) {
            val adUnitDao = database?.adUnitDao()
            adUnitDao?.edit(adUnit)
        }
        return 0
    }

    override fun onPostExecute(result: Long?) {
        super.onPostExecute(result)
        callback?.onFinished()
    }

    interface EditCallback {
        fun onFinished()
    }
}