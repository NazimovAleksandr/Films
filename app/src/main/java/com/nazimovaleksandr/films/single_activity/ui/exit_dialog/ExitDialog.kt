package com.nazimovaleksandr.films.single_activity.ui.exit_dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.nazimovaleksandr.films.R

class ExitDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
            .setMessage(getString(R.string.text_exit_dialog))
            .setNegativeButton(getString(android.R.string.cancel)) { _, _ -> }
            .setPositiveButton(getString(android.R.string.ok)) { _, _ ->
                requireActivity().finish()
            }
            .create()
    }
}