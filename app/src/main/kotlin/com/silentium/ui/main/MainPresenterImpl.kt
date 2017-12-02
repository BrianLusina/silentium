package com.silentium.ui.main

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


}