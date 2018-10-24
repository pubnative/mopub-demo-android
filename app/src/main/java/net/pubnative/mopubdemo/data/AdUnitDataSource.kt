package net.pubnative.mopubdemo.data

import net.pubnative.mopubdemo.models.AdUnit

interface AdUnitDataSource {
    interface Callback {
        fun onSuccess(items: List<AdUnit>)
        fun onError(throwable: Throwable)
    }

    fun fetchAll(callback: Callback)

    fun add(adUnit: AdUnit)

    fun edit(adUnit: AdUnit)

    fun remove(adUnit: AdUnit)
}