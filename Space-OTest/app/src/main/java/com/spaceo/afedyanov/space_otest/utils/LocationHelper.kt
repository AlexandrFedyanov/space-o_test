package com.spaceo.afedyanov.space_otest.utils

import android.content.Context
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.spaceo.afedyanov.space_otest.listener.LocationChangeListener

/**
 * Created by Alexandr on 08.06.2016.
 */
class LocationHelper(private var context: Context?) : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private var googleApiClient: GoogleApiClient? = null
    private var locationChangeListener: LocationChangeListener? = null
    private var latitude = 0.0
    private var longitude = 0.0

    init {
        initGoogleApiClient()
    }

    fun setLocationChangeListener(locationChangeListener: LocationChangeListener) {
        this.locationChangeListener = locationChangeListener
    }

    private fun initGoogleApiClient() {
        context ?: return
        googleApiClient = GoogleApiClient.Builder(context!!).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build()
        googleApiClient?.connect()
    }

    fun requestLocation() {
        findLocationByGoogleApi()
    }

    fun getCurrentLocation() : Array<Double> {
        return arrayOf(latitude, longitude)
    }

    private fun findLocationByGoogleApi() {
        if (googleApiClient != null && googleApiClient!!.isConnected) {
            val location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude
                locationChangeListener?.onLocationChanged(latitude, longitude)
            }
        }
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnected(p0: Bundle?) {
        requestLocation()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    fun cleanUp() {
        locationChangeListener = null
        googleApiClient?.disconnect()
        googleApiClient = null
        context = null
    }

}