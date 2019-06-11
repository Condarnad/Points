package ru.test.points.repository.points

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.test.points.repository.points.db.PointsRealm
import ru.test.points.repository.points.network.PointsApi
import javax.inject.Singleton

@Module
class PointsModule {

    @Provides
    fun providePointsApi(retrofit: Retrofit) = retrofit.create(PointsApi::class.java)

    @Provides
    fun providePointsRealm() = PointsRealm()

}