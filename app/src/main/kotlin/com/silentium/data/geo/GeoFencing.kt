package com.silentium.data.geo

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Result
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.PlaceBuffer
import com.silentium.receivers.GeofenceBroadcastReceiver
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import java.util.*

/**
 * @author lusinabrian on 02/12/17.
 * @Notes GeoFencing
 */
class GeoFencing (): ResultCallback<Result>, AnkoLogger{
    private val GEOFENCE_RADIUS = 50f // 50 meters
    private val GEOFENCE_TIMEOUT = (24 * 60 * 60 * 1000).toLong() // 24 hours

    lateinit var mGeofenceList: MutableList<Geofence>
    var mGeofencePendingIntent: PendingIntent? = null
    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var mContext: Context

    constructor(context: Context, client: GoogleApiClient): this(){
        mContext = context
        mGoogleApiClient = client
        mGeofencePendingIntent = null
        mGeofenceList = ArrayList()
    }

    /***
     * Registers the list of Geofences specified in mGeofenceList with Google Place Services
     * Uses `#mGoogleApiClient` to connect to Google Place Services
     * Uses [.getGeofencingRequest] to get the list of Geofences to be registered
     * Uses [.getGeofencePendingIntent] to get the pending intent to launch the IntentService
     * when the Geofence is triggered
     * Triggers [.onResult] when the geofences have been registered successfully
     */
    fun registerAllGeofences() {
        // Check that the API client is connected and that the list has Geofences in it
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected() ||
                mGeofenceList == null || mGeofenceList.size == 0) {
            return
        }
        try {
            LocationServices.GeofencingApi.addGeofences(mGoogleApiClient,
                    getGeofencingRequest(), getGeofencePendingIntent()).setResultCallback(this)
        } catch (securityException: SecurityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            error(securityException.message)
        }

    }

    /***
     * Unregisters all the Geofences created by this app from Google Place Services
     * Uses `#mGoogleApiClient` to connect to Google Place Services
     * Uses [.getGeofencePendingIntent] to get the pending intent passed when
     * registering the Geofences in the first place
     * Triggers [.onResult] when the geofences have been unregistered successfully
     */
    fun unRegisterAllGeofences() {
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {
            return
        }
        try {
            LocationServices.GeofencingApi.removeGeofences(
                    mGoogleApiClient,
                    // This is the same pending intent that was used in registerGeofences
                    getGeofencePendingIntent()
            ).setResultCallback(this)
        } catch (securityException: SecurityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            error(securityException.message)
        }

    }


    /***
     * Updates the local ArrayList of Geofences using data from the passed in list
     * Uses the Place ID defined by the API as the Geofence object Id
     *
     * @param places the PlaceBuffer result of the getPlaceById call
     */
    fun updateGeofencesList(places: PlaceBuffer?) {
        mGeofenceList = ArrayList()
        if (places == null || places.count == 0) {
            return
        }
        for (place in places) {
            // Read the place information from the DB cursor
            val placeUID = place.id
            val placeLat = place.latLng.latitude
            val placeLng = place.latLng.longitude
            // Build a Geofence object
            val geofence = Geofence.Builder()
                    .setRequestId(placeUID)
                    .setExpirationDuration(GEOFENCE_TIMEOUT)
                    .setCircularRegion(placeLat, placeLng, GEOFENCE_RADIUS)
                    .setTransitionTypes(
                            Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build()
            // Add it to the list
            mGeofenceList.add(geofence)
        }
    }

    /***
     * Creates a GeofencingRequest object using the mGeofenceList ArrayList of Geofences
     * Used by `#registerGeofences`
     *
     * @return the GeofencingRequest object
     */
    private fun getGeofencingRequest(): GeofencingRequest {
        val builder = GeofencingRequest.Builder()
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        builder.addGeofences(mGeofenceList)
        return builder.build()
    }

    /***
     * Creates a PendingIntent object using the GeofenceTransitionsIntentService class
     * Used by `#registerGeofences`
     *
     * @return the PendingIntent object
     */
    private fun getGeofencePendingIntent(): PendingIntent? {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent as PendingIntent
        }
        val intent = Intent(mContext, GeofenceBroadcastReceiver::class.java)
        mGeofencePendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        return mGeofencePendingIntent
    }

    override fun onResult(result: Result) {
        error(String.format("Error adding/removing geofence : %s", result.status.toString()))
    }
}