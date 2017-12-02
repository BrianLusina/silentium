package com.silentium.ui.main

import com.google.android.gms.location.places.Place
import com.silentium.data.DataManager
import com.silentium.data.io.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Main Presenter implementation that connects the view to the data layer
 */
class MainPresenterImpl<V : MainView>
@Inject
constructor(private val dataManager: DataManager,
            private val compositeDisposable: CompositeDisposable,
            private val schedulerProvider: SchedulerProvider): MainPresenter<V>, AnkoLogger{

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

    override fun onGetPlaceById(placeId: String) {
        compositeDisposable.add(
                dataManager.getPlaceById(placeId)
                        .observeOn(schedulerProvider.ui())
                        .subscribeOn(schedulerProvider.io())
                        .subscribe({
                            // on next
                            mMainView.updateAdapter(it)
                            mMainView.registerGeoFenceForPlace(it.id)
                        },{
                            // on error
                            mMainView.displayError("Failed to retrieve places")
                            error("Failed to get places ${it.message}", it)
                        }, {
                            // on complete
                            info { "on Complete" }
                        })
        )
    }

    override fun onGetPlaces() {
        compositeDisposable.add(
                dataManager.getPlaces()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOn(schedulerProvider.io())
                        .subscribe({
                            // on next
                            it.forEach{
                                mMainView.updateAdapter(it)
                                mMainView.registerGeoFenceForPlace(it.id)
                            }
                        },{
                            // on error
                            mMainView.displayError("Failed to retrieve places")
                            error("Failed to get places ${it.message}", it)
                        }, {
                            // on complete
                            info { "on Complete" }
                        })
        )
    }

    override fun onDetach() {
        compositeDisposable.dispose()
    }
}