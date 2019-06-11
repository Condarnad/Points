package ru.test.points.ui.activities.details

import dagger.Module
import dagger.Provides
import ru.test.points.base.dagger.scope.ActivityScope
import ru.test.points.model.points.DepositionPointFullInfo
import ru.test.points.ui.utils.Extra

@Module
class DetailsModule {

    @ActivityScope
    @Provides
    fun providePoint(activity: DetailsActivity) =
        activity.intent.getParcelableExtra<DepositionPointFullInfo>(Extra.FIRST)

}