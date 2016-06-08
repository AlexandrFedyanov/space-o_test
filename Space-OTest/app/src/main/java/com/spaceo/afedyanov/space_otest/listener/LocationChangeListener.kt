package com.spaceo.afedyanov.space_otest.listener

/**
 * Created by Alexandr on 08.06.2016.
 */
interface LocationChangeListener {
    fun onLocationChanged(latitude: Double, longitude: Double)
}