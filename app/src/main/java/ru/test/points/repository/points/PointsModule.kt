package ru.test.points.repository.points

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.test.points.base.dagger.db.AppDatabase
import ru.test.points.repository.points.db.PointsDao
import ru.test.points.repository.points.network.PointsApi

@Module
class PointsModule {

    @Provides
    fun providePointsApi(retrofit: Retrofit) = retrofit.create(PointsApi::class.java)

    @Provides
    fun providePointsDao(appDatabase: AppDatabase) = appDatabase.pointsDao

    @Provides
    fun providePointsInfoDao(appDatabase: AppDatabase) = appDatabase.pointsInfoDao
}