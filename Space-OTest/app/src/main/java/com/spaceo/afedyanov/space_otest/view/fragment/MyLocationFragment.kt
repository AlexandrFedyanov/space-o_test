package com.spaceo.afedyanov.space_otest.view.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.spaceo.afedyanov.space_otest.R
import com.spaceo.afedyanov.space_otest.listener.LocationChangeListener
import com.spaceo.afedyanov.space_otest.utils.LocationHelper
import kotlinx.android.synthetic.main.fragment_map.*

/**
 * Created by Alexandr on 06.06.2016.
 */
class MyLocationFragment : BaseFragment(), OnMapReadyCallback {

    private var map: GoogleMap? = null
    private var locationHelper: LocationHelper? = null
    private var ACCESS_LOCATION_CORE_REQUEST = 32112

    companion object {
        fun newInstance() : MyLocationFragment {
            return MyLocationFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_map, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        locationHelper?.cleanUp()
    }

    override fun setupLayout() {
        val mapFragment: MapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this);
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)  == PackageManager.PERMISSION_GRANTED)
            setupLocationHelper()
        else {
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    ACCESS_LOCATION_CORE_REQUEST);
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        when (requestCode) {
            ACCESS_LOCATION_CORE_REQUEST -> {
                if (grantResults != null && grantResults.size > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupLocationHelper()
                }
            }
        }
    }

    fun setupLocationHelper() {
        locationHelper = LocationHelper(activity)
        locationHelper?.setLocationChangeListener(object: LocationChangeListener {
            override fun onLocationChanged(latitude: Double, longitude: Double) {
                showMyLocation(latitude, longitude)
            }
        })
    }

    override fun scrollContentToTop() {
        locationHelper ?: return
        val currentLocation = locationHelper?.getCurrentLocation()
        map?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(currentLocation!![0], currentLocation[1])))
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        locationHelper?.requestLocation()
    }

    fun showMyLocation(latitude: Double, longitude: Double) {
        if (!isAdded) {
            showMyLocationText(latitude, longitude)
            showLocationMarkerOnMap(latitude, longitude)
        }
    }

    fun showMyLocationText(latitude: Double, longitude: Double) {
        myLocationText.text = getString(R.string.my_location_formatted_text, latitude, longitude)
    }

    fun showLocationMarkerOnMap(latitude: Double, longitude: Double) {
        map?.addMarker(MarkerOptions()
                .position(LatLng(latitude, longitude))
                .title(getString(R.string.my_location_marker)))?.showInfoWindow();
        map?.moveCamera(CameraUpdateFactory.zoomTo(14f))
        map?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude, longitude)))
        map?.setOnMapClickListener { openGoogleMap(latitude, longitude, 16f) }
    }

    fun openGoogleMap(latitude: Double, longitude: Double, zoom: Float) {
        val gmmIntentUri : Uri = Uri.parse("geo:$latitude,$longitude?z=$zoom");
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.`package` = "com.google.android.apps.maps";
        startActivity(mapIntent);
    }
}