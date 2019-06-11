package ru.test.points.repository.partners.network

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.test.points.model.partners.Partner

interface PartnersApi {

    @GET("deposition_partners")
    fun getDepositionPartners(
        @Query("accountType") accountType: String
    ): Single<List<Partner>>

}