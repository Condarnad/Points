package ru.test.points.base.dagger

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.test.points.base.App
import ru.test.points.base.dagger.network.NetworkModule
import ru.test.points.base.dagger.network.OkHttpModule
import ru.test.points.repository.partners.PartnersModule
import ru.test.points.repository.points.PointsModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AndroidInjectionModule::class),
        (AndroidSupportInjectionModule::class),
        (AppModule::class),
        (NetworkModule::class),
        (OkHttpModule::class),
        (ActivitiesModule::class),

        (PointsModule::class)
    ]
)

interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }

}