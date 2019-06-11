package ru.test.points.base.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.test.points.base.dagger.scope.ActivityScope
import ru.test.points.base.dagger.scope.FragmentScope
import ru.test.points.ui.fragments.list.ListFragment
import ru.test.points.ui.fragments.map.MapFragment

@Module
abstract class MainFragmentsModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindMap(): MapFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindList(): ListFragment
}