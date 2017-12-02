package com.silentium.ui.main

import com.google.android.gms.location.places.Place
import com.silentium.data.DataManager
import com.silentium.data.io.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Main Presenter implementation that connects the view to the data layer
 */
class MainPresenterImpl<V : MainView>
@Inject
constructor(val dataManager: DataManager,
            val compositeDisposable: CompositeDisposable,
            val schedulerProvider: SchedulerProvider): MainPresenter<V>{

    lateinit var mMainView : V
        private set

    override fun onAttach(mainView: V) {
        this.mMainView = mainView

        mMainView.setRecyclerAdapter()
        mMainView.setListeners()
    }

    override fun onResume() {
        mMainView.setCheckBoxPermissions()
    }

    override fun onLocationPermissionClicked() {
        mMainView.requestLocationPermissions()
    }

    override fun onRingerPermissionClicked() {
        mMainView.openRingerSettings()
    }

    override fun onEnableGeoFencesChecked(enabled: Boolean) {
        dataManager.setGeoFencesEnabled(enabled)
    }

    override fun onAddPlaceToDatabase(place: Place) {
        dataManager.addPlaceToDatabase(place)
    }

    override fun isGeoFencesEnabled() = dataManager.getGeoFencesEnabled()

    override fun onAddNewLocationClicked() {
        mMainView.addNewPlace()
    }

    override fun onDetach() {
        compositeDisposable.dispose()
    }
}