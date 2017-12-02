package com.silentium.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.silentium.R
import com.silentium.app.SilentiumApp
import com.silentium.di.components.ActivityComponent
import com.silentium.di.components.DaggerActivityComponent
import com.silentium.ui.Geofencing
import com.silentium.ui.PlaceListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Main activity of application
 */
class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, MainView {

    private val activityComponent : ActivityComponent by lazy {
        DaggerActivityComponent.builder()
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

    override fun setRecyclerAdapter() {
        places_list_recycler_view.layoutManager = LinearLayoutManager(this)
        places_list_recycler_view.adapter = placeListAdapter
    }

    override fun onConnected(bundle: Bundle?) {
    }

    override fun onConnectionSuspended(cause: Int) {
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
    }

}