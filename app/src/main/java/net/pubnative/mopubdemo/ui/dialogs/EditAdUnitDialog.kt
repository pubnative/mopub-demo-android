package net.pubnative.mopubdemo.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import net.pubnative.mopubdemo.R
import net.pubnative.mopubdemo.models.AdUnit

class EditAdUnitDialog: DialogFragment() {
    interface EditDialogListener {
        fun onSubmitEdit(adUnit: AdUnit)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)

        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_edit_ad_unit, null, false)

        builder.setTitle(R.string.title_edit_ad_unit)
        builder.setView(view)
        builder.setNegativeButton(R.string.action_cancel) { _, _ ->

        }
        builder.setPositiveButton(R.string.action_insert) { _, _ ->

        }

        return builder.create()
    }

    private fun sendResult(adUnit: AdUnit) {
        val listener = targetFragment as EditDialogListener?
        listener?.onSubmitEdit(adUnit)
        dismiss()
    }

    companion object {
        const val ARG_AD_UNIT = "ad_unit"

        fun newInstance(adUnit: AdUnit): CreateAdUnitDialog {
            val dialog = CreateAdUnitDialog()
            val args = Bundle()
            args.putParcelable(ARG_AD_UNIT, adUnit)
            dialog.arguments = args
            return dialog
        }
    }
}