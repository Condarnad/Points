package ru.test.points.base

import android.util.Log
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import ru.test.points.base.dagger.AppComponent
import ru.test.points.base.dagger.AppModule
import ru.test.points.base.dagger.DaggerAppComponent

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .appModule(AppModule(this))
            .build().apply { inject(this@App) }
        return appComponent
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()


        RxJavaPlugins.setErrorHandler {
            Log.w("RxError", it)
        }
    }
}