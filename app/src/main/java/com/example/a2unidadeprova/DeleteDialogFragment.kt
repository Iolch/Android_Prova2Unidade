package com.example.a2unidadeprova

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class DeleteDialogFragment : DialogFragment(){
    private var listener: OnDialoListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = androidx.appcompat.app.AlertDialog.Builder(activity!!)
        builder.setTitle("Deseja Deletar?")
        builder.setPositiveButton("SIM") { dialogInterface, i ->
            listener!!.onOK()
        }

        builder.setNegativeButton("Cancelar") { dialogInterface, i -> dismiss() }

        builder.setView(view)
        return builder.create()
    }
    interface OnDialoListener {
        fun onOK()
    }
    companion object {
        fun show(fm: FragmentManager, listener: OnDialoListener) {

            val dialog = DeleteDialogFragment()
            dialog.listener = listener
            dialog.show(fm, "textDialog")

        }
    }
}
