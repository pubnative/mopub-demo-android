package net.pubnative.mopubdemo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_nav_ad_unit.*
import net.pubnative.mopubdemo.R
import net.pubnative.mopubdemo.data.AdUnitRepository
import net.pubnative.mopubdemo.listeners.AdUnitClickListener
import net.pubnative.mopubdemo.managers.SettingsManager
import net.pubnative.mopubdemo.models.*
import net.pubnative.mopubdemo.ui.adapters.AdUnitAdapter

class AdUnitNavFragment : Fragment(), AdUnitClickListener {
    companion object {
        private val TAG = AdsNavFragment::class.java.simpleName
    }

    private lateinit var adapter: AdUnitAdapter
    private var adUnit: AdUnit? = null

    private lateinit var adUnitRepository: AdUnitRepository
    private lateinit var settingsManager: SettingsManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nav_ad_unit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adUnitRepository = AdUnitRepository(activity!!)
        settingsManager = SettingsManager(activity!!)

        setupAdUnit(settingsManager.getSelectedAdUnit())

        adapter = AdUnitAdapter(this)
        list_ad_units.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        list_ad_units.itemAnimator = DefaultItemAnimator()
        list_ad_units.adapter = adapter

        loadAdUnits()

        button_add_ad_unit.setOnClickListener {

        }
    }

    private fun setupAdUnit(adUnit: AdUnit) {
        view_ad_unit_name.text = adUnit.name
        view_ad_unit_id.text = adUnit.adUnitId


        view_ad_unit_name.text = when (adUnit.adSize) {
            BANNER -> getString(R.string.ad_size_banner_simple)
            MRECT -> getString(R.string.ad_size_mrect_simple)
            LEADERBOARD -> getString(R.string.ad_size_leaderboard_simple)
            INTERSTITIAL -> getString(R.string.ad_size_interstitial_simple)
            else -> getString(R.string.ad_size_banner_simple)
        }
    }

    private fun loadAdUnits() {

    }

    override fun onAdUnitClick(adUnit: AdUnit) {
        settingsManager.setSelectedAdUnit(adUnit)
        setupAdUnit(adUnit)

    }

    override fun onAdUnitEdit(adUnit: AdUnit) {
        showEditDialog(adUnit)
    }

    override fun onAdUnitRemove(adUnit: AdUnit) {
        showConfirmRemoveDialog(adUnit)
    }

    private fun showInsertDialog() {

    }

    private fun showEditDialog(adUnit: AdUnit) {

    }

    private fun showConfirmRemoveDialog(adUnit: AdUnit) {

    }
}