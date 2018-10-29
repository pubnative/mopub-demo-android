package net.pubnative.mopubdemo.ui.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import net.pubnative.mopubdemo.R
import net.pubnative.mopubdemo.models.*

class CreateAdUnitDialog : DialogFragment() {
    interface CreateDialogListener {
        fun onSubmitCreate(adUnit: AdUnit)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!)

        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_create_ad_unit, null, false)

        builder.setTitle(R.string.title_insert_ad_unit)
        builder.setView(view)
        builder.setNegativeButton(R.string.action_cancel) { _, _ ->
            dismiss()
        }
        builder.setPositiveButton(R.string.action_insert) { _, _ ->
            val name = view.findViewById<EditText>(R.id.input_ad_unit_name).text.toString()
            val id = view.findViewById<EditText>(R.id.input_ad_unit_id).text.toString()

            val size = when (view.findViewById<RadioGroup>(R.id.group_ad_unit_size).checkedRadioButtonId) {
                R.id.radio_banner -> BANNER
                R.id.radio_mrect -> MRECT
                R.id.radio_leaderboard -> LEADERBOARD
                R.id.radio_interstitial -> INTERSTITIAL
                else -> UNDEFINED
            }

            if (validateInput(name, id, size)) {
                sendResult(AdUnit(name, id, size))
            } else {
                Snackbar.make(view, R.string.error_field_validation, Snackbar.LENGTH_SHORT).show()
            }
        }

        return builder.create()
    }

    private fun validateInput(name: String, adUnitId: String, size: Int): Boolean {
        return name.isNotEmpty() && adUnitId.isNotEmpty() && size != UNDEFINED
    }

    private fun sendResult(adUnit: AdUnit) {
        val listener = targetFragment as CreateDialogListener?
        listener?.onSubmitCreate(adUnit)
        dismiss()
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