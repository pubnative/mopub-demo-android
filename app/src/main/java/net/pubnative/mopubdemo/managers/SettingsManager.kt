package net.pubnative.mopubdemo.managers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import net.pubnative.mopubdemo.R
import net.pubnative.mopubdemo.models.AdUnit
import net.pubnative.mopubdemo.models.BANNER

class SettingsManager(private val context: Context) {
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun setSelectedAdUnit(adUnit: AdUnit) {
        val editor = preferences.edit()
        editor.putString(PREF_AD_UNIT_NAME, adUnit.name)
        editor.putString(PREF_AD_UNIT_ID, adUnit.adUnitId)
        editor.putInt(PREF_AD_UNIT_SIZE, adUnit.adSize)
        editor.apply()
    }

    fun getSelectedAdUnit(): AdUnit {
        val adUnitName = preferences.getString(PREF_AD_UNIT_NAME, context.getString(R.string.ad_unit_name_default_banner))
        val adUnitId = preferences.getString(PREF_AD_UNIT_ID, context.getString(R.string.ad_unit_id_default_banner))
        val adUnitSize = preferences.getInt(PREF_AD_UNIT_SIZE, BANNER)

        return AdUnit(adUnitName!!, adUnitId!!, adUnitSize)
    }

    fun areAdUnitsInitialised(): Boolean {
        return preferences.contains(PREF_AD_UNITS_INITIALIZED) && preferences.getBoolean(PREF_AD_UNITS_INITIALIZED, false)
    }

    companion object {
        private const val PREF_AD_UNIT_NAME = "ad_unit_name"
        private const val PREF_AD_UNIT_ID = "ad_unit_id"
        private const val PREF_AD_UNIT_SIZE = "ad_unit_size"

        private const val PREF_AD_UNITS_INITIALIZED = "ad_units_initialized"
    }
}