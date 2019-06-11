package ru.test.points.base.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.test.points.base.App

@Module
class AppModule(app: App) {

    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

}