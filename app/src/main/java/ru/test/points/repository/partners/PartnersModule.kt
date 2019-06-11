package ru.test.points.repository.partners

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.test.points.repository.partners.network.PartnersApi
import ru.test.points.repository.points.network.PointsApi

@Module
class PartnersModule {

    @Provides
    fun providePartnersApi(retrofit: Retrofit) = retrofit.create(PartnersApi::class.java)
}