package net.pubnative.mopubdemo.ui.viewholders

import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import net.pubnative.mopubdemo.R
import net.pubnative.mopubdemo.listeners.AdUnitClickListener
import net.pubnative.mopubdemo.models.AdUnit

class AdUnitViewHolder(itemView: View, private val listener: AdUnitClickListener?) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener,
    PopupMenu.OnMenuItemClickListener {
    private var adUnit: AdUnit? = null

    fun bind(adUnit: AdUnit) {
        this.adUnit = adUnit

        itemView.setOnClickListener(this)
        itemView.findViewById<ImageButton>(R.id.button_more).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_more -> showPopupMenu()
            else -> listener?.onAdUnitClick(adUnit!!)
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_edit -> listener?.onAdUnitEdit(adUnit!!)
            R.id.action_remove -> listener?.onAdUnitRemove(adUnit!!)
        }

        return true
    }

    private fun showPopupMenu() {
        val menu = PopupMenu(itemView.context, itemView.findViewById(R.id.button_more))
        menu.menuInflater.inflate(R.menu.popup_ad_unit, menu.menu)
        menu.setOnMenuItemClickListener(this)
    }
}