package ru.test.points.base.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.test.points.base.dagger.scope.ActivityScope
import ru.test.points.ui.activities.main.MainActivity

@Module(includes = [(FragmentsModule::class)])
abstract class ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

}