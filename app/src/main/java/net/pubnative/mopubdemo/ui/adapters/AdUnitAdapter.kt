package net.pubnative.mopubdemo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.pubnative.mopubdemo.R
import net.pubnative.mopubdemo.listeners.AdUnitClickListener
import net.pubnative.mopubdemo.models.AdUnit
import net.pubnative.mopubdemo.ui.viewholders.AdUnitViewHolder

class AdUnitAdapter(private val listener: AdUnitClickListener?) : RecyclerView.Adapter<AdUnitViewHolder>() {
    private val list: MutableList<AdUnit> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdUnitViewHolder {
        return AdUnitViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ad_unit, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: AdUnitViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    fun delete(index: Int) {
        list.removeAt(index)
        notifyItemRemoved(index)
    }

    fun delete(adUnit: AdUnit) {
        val index = list.indexOf(adUnit)
        if (index != -1) {
            delete(index)
        }
    }

    fun add(adUnit: AdUnit) {
        list.add(adUnit)
        notifyItemInserted(list.size - 1)
    }

    fun addAll(items: List<AdUnit>) {
        items.forEach { add(it) }
    }
}