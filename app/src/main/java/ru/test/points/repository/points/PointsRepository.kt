package ru.test.points.repository.points

import io.reactivex.Completable
import io.reactivex.Single
import ru.test.points.model.common.GeoPoint
import ru.test.points.model.common.GeoPointBounds
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointInfo
import ru.test.points.repository.partners.PartnersRepository
import ru.test.points.repository.points.db.PointsDao
import ru.test.points.repository.points.db.PointsInfoDao
import ru.test.points.repository.points.network.PointsApi
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PointsRepository @Inject constructor(
    private val pointsDao: PointsDao,
    private val pointsInfoDao: PointsInfoDao,
    private val pointsApi: PointsApi,
    private val partnersRepository: PartnersRepository
) {

    private val CACHE_TIME = 10 * 60 * 1000

    private fun clearCacheAndGetInBounds(bounds: GeoPointBounds) =
        Completable
            .fromAction {
                pointsDao.deleteByTime(Calendar.getInstance().timeInMillis - CACHE_TIME)
            }
            .andThen(
                pointsDao.getAllInBoundsFullInfo(
                    bounds.northeast.latitude,
                    bounds.northeast.longitude,
                    bounds.southwest.latitude,
                    bounds.southwest.longitude
                )
            )

    fun getPointsWithFullInfo(bounds: GeoPointBounds) =
        pointsApi.getDepositionPoints(
            bounds.getCenter().latitude,
            bounds.getCenter().longitude,
            bounds.northeast.distanceBetween(bounds.southwest).toInt(),
            null
        ).map {
            pointsDao.insertAll(it)
            partnersRepository.syncPartners().blockingGet()
            clearCacheAndGetInBounds(bounds).blockingGet()
        }.onErrorResumeNext(clearCacheAndGetInBounds(bounds))

    fun getPointFullInfo(externalId: String) =
        pointsDao.getPointFullInfo(externalId)

    fun setPointVisitedStatus(point: DepositionPoint, isVisited: Boolean) =
        pointsInfoDao.insert(DepositionPointInfo(point.externalId, isVisited))
}