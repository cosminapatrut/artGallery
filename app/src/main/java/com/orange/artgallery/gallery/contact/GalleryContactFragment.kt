package com.orange.artgallery.gallery.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.orange.artgallery.R
import com.orange.artgallery.util.readAssetFile
import kotlinx.android.synthetic.main.fragment_gallery_contact.*

class GalleryContactFragment : Fragment(), OnMapReadyCallback {

    private lateinit var galleryContactInfo: GalleryContactModel
    private lateinit var mapFragment: SupportMapFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.contact_map) as SupportMapFragment

        readGalleryContactInfo()
    }

    private fun readGalleryContactInfo() {
        context?.let {
            val galleryContactFileOutput = it.readAssetFile("gallery_contact.json")
            val gson = Gson()
            galleryContactInfo = gson.fromJson(
                galleryContactFileOutput,
                GalleryContactModel::class.java
            )
            displayGalleryInfo()
            mapFragment.getMapAsync(this)
        }
    }

    private fun displayGalleryInfo() {
        with(galleryContactInfo) {
            gallery_title.text = title
            gallery_location.text = city
            gallery_description.text = description
            gallery_contact.text = contact
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            val location = LatLng(
                galleryContactInfo.latitude.toDouble(),
                galleryContactInfo.longitude.toDouble()
            )

            it.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(galleryContactInfo.title)
            )

            it.moveCamera(
                CameraUpdateFactory.newLatLngZoom(location, 16f)
            )
        }
    }

    companion object {
        fun newInstance() = GalleryContactFragment()
    }
}