package ru.test.points.repository.points

import io.reactivex.Observable
import io.reactivex.Single
import io.realm.Realm
import ru.test.points.model.common.GeoPoint
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointExtended
import ru.test.points.repository.partners.PartnersRepository
import ru.test.points.repository.points.db.PointsRealm
import ru.test.points.repository.points.network.PointsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PointsRepository @Inject constructor(
    val partnersRepository: PartnersRepository,
    val pointsRealm: PointsRealm,
    val pointsApi: PointsApi
) {

    fun getPoints(geoPoint: GeoPoint, radius: Int) =
        pointsRealm.getPointsByRadiusAsync(geoPoint, radius)
            .concatWith(
                pointsApi
                    .getDepositionPoints(geoPoint.latitude, geoPoint.longitude, radius, null)
                    .map {
                        pointsRealm.insertPoints(it)
                        pointsRealm.getPointsByRadius(geoPoint, radius)
                    }
            )

    // server if not in db
    //fun getPoint()

    // server if not in db
    //fun getPartners


    fun getPointInfo(point: DepositionPointExtended) {
        val externalId = point.externalId
        val partnerId = point.partnerName

        //Single.zip(
        //    points
       // )
    }
}