package net.pubnative.mopubdemo.data

import net.pubnative.mopubdemo.models.AdUnit

interface AdUnitDataSource {
    interface FetchCallback {
        fun onSuccess(items: List<AdUnit>)
        fun onError(throwable: Throwable)
    }

    interface AddCallback {
        fun onSuccess()
        fun onError(throwable: Throwable)
    }

    fun fetchAll(callback: FetchCallback)

    fun add(adUnit: AdUnit, callback: AddCallback)

    fun edit(adUnit: AdUnit)

    fun remove(adUnit: AdUnit)
}