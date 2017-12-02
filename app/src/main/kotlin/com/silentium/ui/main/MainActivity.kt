package com.silentium.ui.main

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlacePicker
import com.silentium.R
import com.silentium.app.SilentiumApp
import com.silentium.data.db.provider.PlaceContract
import com.silentium.di.components.ActivityComponent
import com.silentium.di.components.DaggerActivityComponent
import com.silentium.di.modules.ActivityModule
import com.silentium.ui.Geofencing
import com.silentium.ui.PlaceListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import org.jetbrains.anko.longToast
import java.util.*
import javax.inject.Inject

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Main activity of application
 */
class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, MainView, View.OnClickListener, AnkoLogger {

    private val permissionsRequestFineLocationCode = 111
    private val placePickerRequestCode = 1

    override val loggerTag: String
        get() = super.loggerTag

    private val activityComponent : ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .appComponent((application as SilentiumApp).appComponent)
                .build()
    }

    @Inject
    lateinit var mainPresenter : MainPresenter<MainView>

    @Inject
    lateinit var placeListAdapter : PlaceListAdapter

    private val googleApiClient : GoogleApiClient by lazy {
        GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build()
    }

    private val geoFencing : Geofencing by lazy {
        Geofencing(this, googleApiClient)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityComponent.injectMain(this)

        mainPresenter.onAttach(this)
    }

    override fun onResume() {
        super.onResume()
        mainPresenter.onResume()
    }

    override fun setRecyclerAdapter() {
        places_list_recycler_view.layoutManager = LinearLayoutManager(this)
        places_list_recycler_view.adapter = placeListAdapter
    }

    override fun setListeners() {
        enable_switch.setOnCheckedChangeListener { _, isChecked ->
            mainPresenter.onEnableGeoFencesChecked(isChecked)
            if (isChecked)
                geoFencing.registerAllGeofences()
            else
                geoFencing.unRegisterAllGeofences()
        }
        ringer_permissions_checkbox.setOnClickListener(this)
        location_permission_checkbox.setOnClickListener(this)
        buttonAddNewLocation.setOnClickListener(this)
    }

    override fun setCheckBoxPermissions() {
        // Initialize location permissions checkbox
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            location_permission_checkbox.isChecked = false
        } else {
            location_permission_checkbox.isChecked = true
            location_permission_checkbox.isEnabled = false
        }

        // Initialize ringer permissions checkbox

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Check if the API supports such permission change and check if permission is granted
        if (Build.VERSION.SDK_INT >= 24 && !nm.isNotificationPolicyAccessGranted) {
            ringer_permissions_checkbox.isChecked = false
        } else {
            ringer_permissions_checkbox.isChecked = true
            ringer_permissions_checkbox.isEnabled = false
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun openRingerSettings() {
        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        startActivity(intent)
    }

    override fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                permissionsRequestFineLocationCode)
    }

    override fun onClick(view: View?) {
        when(view){
            location_permission_checkbox -> {
                mainPresenter.onLocationPermissionClicked()
            }

            ringer_permissions_checkbox -> {
                mainPresenter.onRingerPermissionClicked()
            }

            buttonAddNewLocation -> {
                mainPresenter.onAddNewLocationClicked()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == placePickerRequestCode && resultCode == Activity.RESULT_OK) {
            val place = PlacePicker.getPlace(this, data) ?: return
            // Extract the place information from the API
            val placeName = place.name.toString()
            val placeAddress = place.address.toString()
            val placeID = place.id

            // Insert a new place into DB
            val contentValues = ContentValues()
            contentValues.put(PlaceContract.PlaceEntry.COLUMN_PLACE_ID, placeID)
            contentResolver.insert(PlaceContract.PlaceEntry.CONTENT_URI, contentValues)

            // Get live data information
            refreshPlacesData()
        }
    }

    override fun refreshPlacesData() {
        val uri = PlaceContract.PlaceEntry.CONTENT_URI
        val data = contentResolver.query(
                uri, null, null, null, null)

        if (data == null || data.count == 0) return
        val guids = ArrayList<String>()
        while (data.moveToNext()) {
            guids.add(data.getString(data.getColumnIndex(PlaceContract.PlaceEntry.COLUMN_PLACE_ID)))
        }
        val placeResult = Places.GeoDataApi.getPlaceById(googleApiClient, *guids.toTypedArray())
        placeResult.setResultCallback { places ->
            placeListAdapter.swapPlaces(places)
            geoFencing.updateGeofencesList(places)
            if (mainPresenter.isGeoFencesEnabled())
                geoFencing.registerAllGeofences()
        }
    }

    override fun addNewPlace() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            longToast(getString(R.string.need_location_permission_message))
            return
        }
        try {
            // Start a new Activity for the Place Picker API, this will trigger {@code #onActivityResult}
            // when a place is selected or with the user cancels.
            val builder = PlacePicker.IntentBuilder()
            val i = builder.build(this)
            startActivityForResult(i, placePickerRequestCode)
        } catch (e: GooglePlayServicesRepairableException) {
            error(String.format("GooglePlayServices Not Available [%s]", e.message))
        } catch (e: GooglePlayServicesNotAvailableException) {
            error(String.format("GooglePlayServices Not Available [%s]", e.message))
        } catch (e: Exception) {
            error(String.format("PlacePicker Exception: %s", e.message))
        }
    }

    override fun onConnected(bundle: Bundle?) {
        refreshPlacesData()
        info("Api Client connected")
    }

    override fun onConnectionSuspended(cause: Int) {
        info("Api Client suspended")
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        error("API Client connection failed")
    }

}