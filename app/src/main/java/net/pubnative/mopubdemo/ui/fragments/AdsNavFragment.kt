package net.pubnative.mopubdemo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubInterstitial
import com.mopub.mobileads.MoPubView
import kotlinx.android.synthetic.main.fragment_nav_ads.*
import net.pubnative.mopubdemo.R
import net.pubnative.mopubdemo.managers.SettingsManager
import net.pubnative.mopubdemo.models.*

class AdsNavFragment : Fragment(), MoPubView.BannerAdListener, MoPubInterstitial.InterstitialAdListener {
    companion object {
        private val TAG = AdsNavFragment::class.java.simpleName
    }

    private var adView: MoPubView? = null
    private var interstitial: MoPubInterstitial? = null
    private var adUnit: AdUnit? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nav_ads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdUnit(view)
    }

    private fun setupAdUnit(view: View) {
        val settings = SettingsManager(activity!!)
        adUnit = settings.getSelectedAdUnit()

        view_ad_unit_name.text = adUnit?.name
        view_ad_unit_id.text = adUnit?.adUnitId


         when (adUnit?.adSize) {
            BANNER -> {
                adView = view.findViewById(R.id.ad_banner)
                adView?.adUnitId = adUnit?.adUnitId
                adView?.bannerAdListener = this
                interstitial = null
                button_show_interstitial.visibility = View.GONE
                view_ad_unit_size.text = getString(R.string.ad_size_banner_simple)
            }
            MRECT -> {
                adView = view.findViewById(R.id.ad_mrect)
                adView?.adUnitId = adUnit?.adUnitId
                adView?.bannerAdListener = this
                interstitial = null
                button_show_interstitial.visibility = View.GONE
                view_ad_unit_size.text = getString(R.string.ad_size_mrect_simple)
            }
            LEADERBOARD -> {
                adView = view.findViewById(R.id.ad_leaderboard)
                adView?.adUnitId = adUnit?.adUnitId
                adView?.bannerAdListener = this
                interstitial = null
                button_show_interstitial.visibility = View.GONE
                view_ad_unit_size.text = getString(R.string.ad_size_leaderboard_simple)
            }
            INTERSTITIAL -> {
                adView = null
                interstitial = MoPubInterstitial(activity!!, adUnit?.adUnitId!!)
                interstitial?.interstitialAdListener = this
                button_show_interstitial.visibility = View.VISIBLE
                button_show_interstitial.isEnabled = false
                view_ad_unit_size.text = getString(R.string.ad_size_interstitial_simple)
            }
            else -> {
                adView = null
                interstitial = null
                button_show_interstitial.visibility = View.GONE
                view_ad_unit_size.text = getString(R.string.symbol_empty)
            }
        }

        setAdViewVisibility(view, adUnit?.adSize)
        button_request.setOnClickListener {
            if (adUnit?.adSize == BANNER || adUnit?.adSize == MRECT || adUnit?.adSize == LEADERBOARD) {
                adView?.loadAd()
            } else if (adUnit?.adSize == INTERSTITIAL) {
                interstitial?.load()
            }
        }
        button_show_interstitial.setOnClickListener {
            interstitial?.show()
        }
    }

    private fun setAdViewVisibility(view: View, adSize: Int?) {
        view.findViewById<MoPubView>(R.id.ad_banner).visibility = if (adSize == BANNER) View.VISIBLE else View.GONE
        view.findViewById<MoPubView>(R.id.ad_mrect).visibility = if (adSize == MRECT) View.VISIBLE else View.GONE
        view.findViewById<MoPubView>(R.id.ad_leaderboard).visibility = if (adSize == LEADERBOARD) View.VISIBLE else View.GONE
    }

    // Banner Ad Listener
    override fun onBannerLoaded(banner: MoPubView?) {
        Log.d(TAG, "onBannerLoaded")
    }

    override fun onBannerFailed(banner: MoPubView?, errorCode: MoPubErrorCode?) {
        Log.d(TAG, "onBannerFailed")
    }

    override fun onBannerClicked(banner: MoPubView?) {
        Log.d(TAG, "onBannerClicked")
    }

    override fun onBannerExpanded(banner: MoPubView?) {
        Log.d(TAG, "onBannerExpanded")
    }

    override fun onBannerCollapsed(banner: MoPubView?) {
        Log.d(TAG, "onBannerCollapsed")
    }


    // Interstitial Ad Listener
    override fun onInterstitialLoaded(interstitial: MoPubInterstitial?) {
        button_show_interstitial.isEnabled = true
        Log.d(TAG, "onInterstitialLoaded")
    }

    override fun onInterstitialFailed(interstitial: MoPubInterstitial?, errorCode: MoPubErrorCode?) {
        Log.d(TAG, "onInterstitialFailed")
    }

    override fun onInterstitialShown(interstitial: MoPubInterstitial?) {
        Log.d(TAG, "onInterstitialShown")
    }

    override fun onInterstitialClicked(interstitial: MoPubInterstitial?) {
        Log.d(TAG, "onInterstitialClicked")
    }

    override fun onInterstitialDismissed(interstitial: MoPubInterstitial?) {
        Log.d(TAG, "onInterstitialDismissed")
    }
}