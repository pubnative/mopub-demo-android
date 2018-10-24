package net.pubnative.mopubdemo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_nav_ad_unit.*
import net.pubnative.mopubdemo.R
import net.pubnative.mopubdemo.managers.SettingsManager
import net.pubnative.mopubdemo.models.AdSize
import net.pubnative.mopubdemo.models.AdUnit

class AdUnitNavFragment: Fragment() {
    companion object {
        private val TAG = AdsNavFragment::class.java.simpleName
    }

    private var adUnit: AdUnit? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nav_ad_unit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdUnit()

        button_add_ad_unit.setOnClickListener {

        }
    }

    private fun setupAdUnit() {
        val settings = SettingsManager(activity!!)
        adUnit = settings.getSelectedAdUnit()

        view_ad_unit_name.text = adUnit?.name
        view_ad_unit_id.text = adUnit?.adUnitId


        view_ad_unit_name.text = when (adUnit?.adSize) {
            AdSize.BANNER -> getString(R.string.ad_size_banner_simple)
            AdSize.MRECT -> getString(R.string.ad_size_mrect_simple)
            AdSize.LEADERBOARD -> getString(R.string.ad_size_leaderboard_simple)
            AdSize.INTERSTITIAL -> getString(R.string.ad_size_interstitial_simple)
            else -> getString(R.string.ad_size_banner_simple)
        }
    }
}