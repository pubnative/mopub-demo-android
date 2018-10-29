package net.pubnative.mopubdemo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_nav_ad_unit.*
import net.pubnative.mopubdemo.R
import net.pubnative.mopubdemo.data.AdUnitDataSource
import net.pubnative.mopubdemo.data.AdUnitRepository
import net.pubnative.mopubdemo.listeners.AdUnitClickListener
import net.pubnative.mopubdemo.managers.SettingsManager
import net.pubnative.mopubdemo.models.*
import net.pubnative.mopubdemo.ui.adapters.AdUnitAdapter
import net.pubnative.mopubdemo.ui.dialogs.CreateAdUnitDialog
import net.pubnative.mopubdemo.ui.dialogs.EditAdUnitDialog

class AdUnitNavFragment : Fragment(), AdUnitClickListener, CreateAdUnitDialog.CreateDialogListener, EditAdUnitDialog.EditDialogListener {
    companion object {
        private val TAG = AdsNavFragment::class.java.simpleName

        private const val REQUEST_CREATE = 300
        private const val REQUEST_EDIT = 301
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
            showInsertDialog()
        }
    }

    private fun setupAdUnit(adUnit: AdUnit) {
        view_ad_unit_name.text = adUnit.name
        view_ad_unit_id.text = adUnit.adUnitId


        view_ad_unit_size.text = when (adUnit.adSize) {
            BANNER -> getString(R.string.ad_size_banner_simple)
            MRECT -> getString(R.string.ad_size_mrect_simple)
            LEADERBOARD -> getString(R.string.ad_size_leaderboard_simple)
            INTERSTITIAL -> getString(R.string.ad_size_interstitial_simple)
            else -> getString(R.string.ad_size_banner_simple)
        }
    }

    private fun loadAdUnits() {
        adUnitRepository.fetchAll(object : AdUnitDataSource.FetchCallback {
            override fun onSuccess(items: List<AdUnit>) {
                adapter.clear()
                adapter.addAll(items)
            }

            override fun onError(throwable: Throwable) {
                Snackbar.make(view!!, "There's been an error loading the ad units", Snackbar.LENGTH_SHORT).show()
            }
        })
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
        dialog.show(fragmentManager, "create_dialog")
    }

    private fun showEditDialog(adUnit: AdUnit) {
        val dialog = EditAdUnitDialog.newInstance(adUnit)
        dialog.setTargetFragment(this, REQUEST_EDIT)
        dialog.show(fragmentManager, "create_dialog")
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