package net.pubnative.mopubdemo.ui.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import net.pubnative.mopubdemo.R

class CreateAdUnitDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)

        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_create_ad_unit, null, false)

        builder.setTitle(R.string.title_insert_ad_unit)
        builder.setView(view)
        builder.setNegativeButton(R.string.action_cancel) { _, _ ->

        }
        builder.setPositiveButton(R.string.action_insert) { _, _ ->

        }

        return builder.create()
    }

    companion object {
        fun newInstance(): CreateAdUnitDialog {
            val dialog = CreateAdUnitDialog()
            val args = Bundle()
            dialog.arguments = args
            return dialog
        }
    }
}