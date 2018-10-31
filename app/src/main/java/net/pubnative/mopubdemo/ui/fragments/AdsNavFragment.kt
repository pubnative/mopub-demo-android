package net.pubnative.mopubdemo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_nav_ads.*
import net.pubnative.mopubdemo.R
import net.pubnative.mopubdemo.managers.SettingsManager
import net.pubnative.mopubdemo.models.*

class AdsNavFragment : Fragment() {
    companion object {
        private val TAG = AdsNavFragment::class.java.simpleName
    }

    private var adUnit: AdUnit? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nav_ads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdUnit()
    }

    private fun setupAdUnit() {
        val settings = SettingsManager(activity!!)
        adUnit = settings.getSelectedAdUnit()

        view_ad_unit_name.text = adUnit?.name
        view_ad_unit_id.text = adUnit?.adUnitId


        view_ad_unit_size.text = when (adUnit?.adSize) {
            BANNER -> getString(R.string.ad_size_banner_simple)
            MRECT -> getString(R.string.ad_size_mrect_simple)
            LEADERBOARD -> getString(R.string.ad_size_leaderboard_simple)
            INTERSTITIAL -> getString(R.string.ad_size_interstitial_simple)
            else -> getString(R.string.symbol_empty)
        }

        button_request.setOnClickListener {

        }
    }
}