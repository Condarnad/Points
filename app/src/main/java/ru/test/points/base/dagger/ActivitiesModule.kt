package ru.test.points.base.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.test.points.base.dagger.scope.ActivityScope
import ru.test.points.ui.activities.details.DetailsActivity
import ru.test.points.ui.activities.details.DetailsModule
import ru.test.points.ui.activities.main.MainActivity

@Module()
abstract class ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainFragmentsModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [DetailsModule::class])
    abstract fun bindDetailsActivity(): DetailsActivity
}