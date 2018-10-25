package net.pubnative.mopubdemo.listeners

import net.pubnative.mopubdemo.models.AdUnit

interface AdUnitClickListener {
    fun onAdUnitClick(adUnit: AdUnit)
    fun onAdUnitEdit(adUnit: AdUnit)
    fun onAdUnitRemove(adUnit: AdUnit)
}