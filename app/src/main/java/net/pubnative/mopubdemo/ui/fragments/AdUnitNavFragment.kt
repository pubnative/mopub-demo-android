package net.pubnative.mopubdemo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import net.pubnative.mopubdemo.R
import net.pubnative.mopubdemo.data.AdUnitDataSource
import net.pubnative.mopubdemo.data.AdUnitRepository
import net.pubnative.mopubdemo.listeners.AdUnitClickListener
import net.pubnative.mopubdemo.managers.MoPubManager
import net.pubnative.mopubdemo.managers.SettingsManager
import net.pubnative.mopubdemo.models.*
import net.pubnative.mopubdemo.ui.adapters.AdUnitAdapter
import net.pubnative.mopubdemo.ui.dialogs.CreateAdUnitDialog
import net.pubnative.mopubdemo.ui.dialogs.EditAdUnitDialog

class AdUnitNavFragment : Fragment(), AdUnitClickListener, CreateAdUnitDialog.CreateDialogListener,
    EditAdUnitDialog.EditDialogListener {
    companion object {
        private val TAG = AdsNavFragment::class.java.simpleName

        private const val REQUEST_CREATE = 300
        private const val REQUEST_EDIT = 301
    }

    private lateinit var adapter: AdUnitAdapter
    private var adUnit: AdUnit? = null

    private lateinit var adUnitNameView: TextView
    private lateinit var adUnitIdView: TextView
    private lateinit var adUnitSizeView: TextView
    private lateinit var adUnitList: RecyclerView
    private lateinit var addAdUnitButton: FloatingActionButton
    private lateinit var adUnitRepository: AdUnitRepository
    private lateinit var settingsManager: SettingsManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nav_ad_unit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adUnitNameView = view.findViewById(R.id.view_ad_unit_name)
        adUnitIdView = view.findViewById(R.id.view_ad_unit_id)
        adUnitSizeView = view.findViewById(R.id.view_ad_unit_size)
        adUnitList = view.findViewById(R.id.list_ad_units)
        addAdUnitButton = view.findViewById(R.id.button_add_ad_unit)
        adUnitRepository = AdUnitRepository(activity!!)
        settingsManager = SettingsManager(activity!!)

        setupAdUnit(settingsManager.getSelectedAdUnit())

        adapter = AdUnitAdapter(this)
        adUnitList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        adUnitList.itemAnimator = DefaultItemAnimator()
        adUnitList.adapter = adapter

        loadAdUnits()

        addAdUnitButton.setOnClickListener {
            showInsertDialog()
        }
    }

    private fun setupAdUnit(adUnit: AdUnit) {
        adUnitNameView.text = adUnit.name
        adUnitIdView.text = adUnit.adUnitId


        adUnitSizeView.text = when (adUnit.adSize) {
            BANNER -> getString(R.string.ad_size_banner_simple)
            MRECT -> getString(R.string.ad_size_mrect_simple)
            LEADERBOARD -> getString(R.string.ad_size_leaderboard_simple)
            INTERSTITIAL -> getString(R.string.ad_size_interstitial_simple)
            else -> getString(R.string.symbol_empty)
        }

        MoPubManager.initMoPubSdk(activity!!, adUnit)
    }

    private fun loadAdUnits() {
        adUnitRepository.fetchAll(object : AdUnitDataSource.FetchCallback {
            override fun onSuccess(items: List<AdUnit>) {
                adapter.clear()
                adapter.addAll(injectDefaultAdUnits(items))
            }

            override fun onError(throwable: Throwable) {
                Snackbar.make(view!!, "There's been an error loading the ad units", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun injectDefaultAdUnits(items: List<AdUnit>): List<AdUnit> {
        val tempList = mutableListOf<AdUnit>()

        tempList.add(
            AdUnit(
                getString(R.string.ad_unit_name_default_banner),
                getString(R.string.ad_unit_id_default_banner),
                BANNER,
                true
            )
        )
        tempList.add(
            AdUnit(
                getString(R.string.ad_unit_name_default_mrect),
                getString(R.string.ad_unit_id_default_mrect),
                MRECT,
                true
            )
        )
        tempList.add(
            AdUnit(
                getString(R.string.ad_unit_name_default_leaderboard),
                getString(R.string.ad_unit_id_default_leaderboard),
                LEADERBOARD,
                true
            )
        )
        tempList.add(
            AdUnit(
                getString(R.string.ad_unit_name_default_interstitial),
                getString(R.string.ad_unit_id_default_interstitial),
                INTERSTITIAL,
                true
            )
        )

        tempList.addAll(items)

        return tempList
    }

    override fun onAdUnitClick(adUnit: AdUnit) {
        settingsManager.setSelectedAdUnit(adUnit)
        setupAdUnit(adUnit)
    }

    override fun onAdUnitEdit(adUnit: AdUnit) {
        showEditDialog(adUnit)
    }

    override fun onAdUnitRemove(adUnit: AdUnit) {
        adUnitRepository.remove(adUnit)
        adapter.delete(adUnit)
    }

    private fun showInsertDialog() {
        val dialog = CreateAdUnitDialog.newInstance()
        dialog.setTargetFragment(this, REQUEST_CREATE)
        dialog.show(fragmentManager!!, "create_dialog")
    }

    private fun showEditDialog(adUnit: AdUnit) {
        val dialog = EditAdUnitDialog.newInstance(adUnit)
        dialog.setTargetFragment(this, REQUEST_EDIT)
        dialog.show(fragmentManager!!, "create_dialog")
    }

    override fun onSubmitCreate(adUnit: AdUnit) {
        createAdUnitOnDB(adUnit)
    }

    override fun onSubmitEdit(adUnit: AdUnit) {
        adUnitRepository.edit(adUnit)
    }

    private fun createAdUnitOnDB(adUnit: AdUnit) {
        adUnitRepository.add(adUnit, object : AdUnitDataSource.AddCallback {
            override fun onSuccess() {
                adapter.add(adUnit)
            }

            override fun onError(throwable: Throwable) {
                Snackbar.make(view!!, "There's been an error saving the ad unit", Snackbar.LENGTH_SHORT).show()
            }
        })
    }
}