// InfoDialogFragment.kt
package com.adrian.culinary

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class InfoDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title: TextView = view.findViewById(R.id.info_title)
        val description: TextView = view.findViewById(R.id.info_description)
        val locateMeButton: Button = view.findViewById(R.id.locate_me_button)

        // Set action untuk tombol "Locate Me"
        locateMeButton.setOnClickListener {
            // URL link yang Anda berikan
            val gmmIntentUri = Uri.parse("https://maps.app.goo.gl/wovW2peQ15zZwe7X6")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(mapIntent)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return dialog
    }
}
