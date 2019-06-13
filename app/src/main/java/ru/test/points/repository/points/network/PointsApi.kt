package ru.test.points.repository.points.network

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointDto


interface PointsApi {

    @GET("deposition_points")
    fun getDepositionPoints(
        @Query("latitude") lattitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int,
        @Query("partners") partners: String?
    ): Single<List<DepositionPointDto>>

}