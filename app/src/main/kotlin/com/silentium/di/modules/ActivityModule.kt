package com.silentium.di.modules

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.silentium.data.io.SchedulerProvider
import com.silentium.data.io.SchedulerProviderImpl
import com.silentium.di.qualifiers.ActivityCtxQualifier
import com.silentium.di.scope.ActivityScope
import com.silentium.ui.places.PlaceListAdapter
import com.silentium.ui.main.MainPresenter
import com.silentium.ui.main.MainPresenterImpl
import com.silentium.ui.main.MainView
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * @author lusinabrian on 02/12/17.
 * @Notes Activity Module
 */
@Module
class ActivityModule(private val mActivity : AppCompatActivity) {

    @Provides
    @ActivityCtxQualifier
    fun provideContext(): Context {
        return mActivity
    }

    @Provides
    fun provideSchedulers(): SchedulerProvider {
        return SchedulerProviderImpl()
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    /**
     * Provides the main presenter for the [MainActivity] to consume
     * @param mainPresenter Presenter implementation
     * @return [MainPresenter]
     * */
    @Provides
    @ActivityScope
    fun provideMainPresenter(mainPresenter: MainPresenterImpl<MainView>) : MainPresenter<MainView>{
        return mainPresenter
    }

    @Provides
    fun providePlaceListAdapter(@ActivityCtxQualifier context: Context) : PlaceListAdapter {
        return PlaceListAdapter(context, arrayListOf())
    }
}