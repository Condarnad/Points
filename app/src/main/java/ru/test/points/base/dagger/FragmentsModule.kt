package ru.test.points.base.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.test.points.base.dagger.scope.ActivityScope
import ru.test.points.ui.fragments.map.MapFragment

@Module
abstract class FragmentsModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindMap(): MapFragment

}