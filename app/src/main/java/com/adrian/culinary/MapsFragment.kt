// MapsFragment.kt
package com.adrian.culinary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapsFragment : Fragment() {

    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)

        Configuration.getInstance().load(context,
            context?.let { androidx.preference.PreferenceManager.getDefaultSharedPreferences(it) })
        mapView = view.findViewById(R.id.map)
        mapView.setMultiTouchControls(true)

        // Koordinat Lengkong Culinary Night
        val startPoint = GeoPoint(-6.923220733986437, 107.61368979649735)
        mapView.controller.setZoom(20.0)
        mapView.controller.setCenter(startPoint)

        val marker = Marker(mapView)
        marker.position = startPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Lengkong Culinary Night"
        marker.setOnMarkerClickListener { _, _ ->
            // Tampilkan InfoDialogFragment saat marker ditekan
            val infoDialog = InfoDialogFragment()
            infoDialog.show(parentFragmentManager, "InfoDialog")
            true
        }
        mapView.overlays.add(marker)

        return view
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}
