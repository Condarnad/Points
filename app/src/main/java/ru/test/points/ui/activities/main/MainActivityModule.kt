package ru.test.points.ui.activities.main

import dagger.Module
import dagger.Provides
import io.reactivex.subjects.BehaviorSubject
import ru.test.points.base.dagger.scope.ActivityScope
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointFullInfo

@Module
class MainActivityModule {

    @Provides
    @ActivityScope
    fun providePointsPublisher() = BehaviorSubject.create<List<DepositionPointFullInfo>>()

}