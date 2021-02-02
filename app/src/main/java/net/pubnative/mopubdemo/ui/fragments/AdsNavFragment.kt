package net.pubnative.mopubdemo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubInterstitial
import com.mopub.mobileads.MoPubView
import net.pubnative.mopubdemo.R
import net.pubnative.mopubdemo.managers.SettingsManager
import net.pubnative.mopubdemo.models.*

class AdsNavFragment : Fragment(), MoPubView.BannerAdListener, MoPubInterstitial.InterstitialAdListener {
    companion object {
        private val TAG = AdsNavFragment::class.java.simpleName
    }

    private lateinit var adUnitNameView: TextView
    private lateinit var adUnitIdView: TextView
    private lateinit var adUnitSizeView: TextView
    private lateinit var requestButton: Button
    private lateinit var showInterstitialButton: Button
    private var adView: MoPubView? = null
    private var interstitial: MoPubInterstitial? = null
    private var adUnit: AdUnit? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nav_ads, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adUnitNameView = view.findViewById(R.id.view_ad_unit_name)
        adUnitIdView = view.findViewById(R.id.view_ad_unit_id)
        adUnitSizeView = view.findViewById(R.id.view_ad_unit_size)
        requestButton = view.findViewById(R.id.button_request)
        showInterstitialButton = view.findViewById(R.id.button_show_interstitial)

        setupAdUnit(view)
    }

    private fun setupAdUnit(view: View) {
        val settings = SettingsManager(activity!!)
        adUnit = settings.getSelectedAdUnit()

        adUnitNameView.text = adUnit?.name
        adUnitIdView.text = adUnit?.adUnitId

        adUnit?.let {
            when (adUnit?.adSize) {
                BANNER -> {
                    adView = view.findViewById(R.id.ad_banner)
                    adView?.setAdUnitId(it.adUnitId)
                    adView?.bannerAdListener = this
                    adView?.autorefreshEnabled = false
                    interstitial = null
                    showInterstitialButton.visibility = View.GONE
                    adUnitSizeView.text = getString(R.string.ad_size_banner_simple)
                }
                MRECT -> {
                    adView = view.findViewById(R.id.ad_mrect)
                    adView?.setAdUnitId(it.adUnitId)
                    adView?.bannerAdListener = this
                    adView?.autorefreshEnabled = false
                    interstitial = null
                    showInterstitialButton.visibility = View.GONE
                    adUnitSizeView.text = getString(R.string.ad_size_mrect_simple)
                }
                LEADERBOARD -> {
                    adView = view.findViewById(R.id.ad_leaderboard)
                    adView?.setAdUnitId(it.adUnitId)
                    adView?.bannerAdListener = this
                    adView?.autorefreshEnabled = false
                    interstitial = null
                    showInterstitialButton.visibility = View.GONE
                    adUnitSizeView.text = getString(R.string.ad_size_leaderboard_simple)
                }
                INTERSTITIAL -> {
                    adView = null
                    interstitial = MoPubInterstitial(requireActivity(), it.adUnitId)
                    interstitial?.interstitialAdListener = this
                    showInterstitialButton.visibility = View.VISIBLE
                    showInterstitialButton.isEnabled = false
                    adUnitSizeView.text = getString(R.string.ad_size_interstitial_simple)
                }
                else -> {
                    adView = null
                    interstitial = null
                    showInterstitialButton.visibility = View.GONE
                    adUnitSizeView.text = getString(R.string.symbol_empty)
                }
            }

            setAdViewVisibility(view, it.adSize)
            requestButton.setOnClickListener {
                if (adUnit?.adSize == BANNER || adUnit?.adSize == MRECT || adUnit?.adSize == LEADERBOARD) {
                    adView?.loadAd()
                } else if (adUnit?.adSize == INTERSTITIAL) {
                    interstitial?.load()
                }
            }
            showInterstitialButton.setOnClickListener {
                interstitial?.show()
            }
        }
    }

    private fun setAdViewVisibility(view: View, adSize: Int) {
        view.findViewById<MoPubView>(R.id.ad_banner).visibility = if (adSize == BANNER) View.VISIBLE else View.GONE
        view.findViewById<MoPubView>(R.id.ad_mrect).visibility = if (adSize == MRECT) View.VISIBLE else View.GONE
        view.findViewById<MoPubView>(R.id.ad_leaderboard).visibility = if (adSize == LEADERBOARD) View.VISIBLE else View.GONE
    }

    // Banner Ad Listener
    override fun onBannerLoaded(banner: MoPubView) {
        Log.d(TAG, "onBannerLoaded")
    }

    override fun onBannerFailed(banner: MoPubView?, errorCode: MoPubErrorCode?) {
        Log.d(TAG, "onBannerFailed")
        Toast.makeText(context, errorCode?.name, Toast.LENGTH_SHORT).show()
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
        showInterstitialButton.isEnabled = true
        Log.d(TAG, "onInterstitialLoaded")
    }

    override fun onInterstitialFailed(interstitial: MoPubInterstitial?, errorCode: MoPubErrorCode?) {
        Log.d(TAG, "onInterstitialFailed")
        Toast.makeText(context, errorCode?.name, Toast.LENGTH_SHORT).show()
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