package ru.test.points.repository.points

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import ru.test.points.model.common.GeoPoint
import ru.test.points.model.common.GeoPointBounds
import ru.test.points.model.common.Transformable
import ru.test.points.model.common.transformCollection
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointFullInfo
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

    //10 минут
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

    //Тут при каждом запросе точек выполняется запрос на получение списка партнеров
    //Можно было бы доставать их из базы и сихронизироваться раз в 10 минут, но тогда, если не будет интернета, партнеры очистятся из базы раньше чем точки
    fun getPointsWithFullInfo(bounds: GeoPointBounds) =
        pointsApi
            .getDepositionPoints(
                bounds.getCenter().latitude,
                bounds.getCenter().longitude,
                bounds.northeast.distanceBetween(bounds.southwest).toInt(),
                null
            )
            .transformCollection()
            .map {
                pointsDao.insertAll(it)
                partnersRepository.syncPartners().blockingGet()
                clearCacheAndGetInBounds(bounds).blockingGet()
            }
            .onErrorResumeNext {
                Log.w("RxError", it)
                clearCacheAndGetInBounds(bounds)
            }
            .doOnSuccess {
                pointsPublisher.onNext(it)
            }

    fun getPointFullInfo(externalId: String) =
        pointsDao.getPointFullInfo(externalId)

    fun setPointVisitedStatus(point: DepositionPointFullInfo, isVisited: Boolean) =
        DepositionPointInfo(point.depositionPoint.externalId, isVisited).let { newInfo ->
            pointsInfoDao
                .insert(newInfo)
                .doOnComplete { pointChangedPublisher.onNext(point.apply { depositionPointInfo = newInfo }) }
        }


    private val pointsPublisher = BehaviorSubject.create<List<DepositionPointFullInfo>>()
    fun observeRecentPoints() = pointsPublisher

    private val pointChangedPublisher = BehaviorSubject.create<DepositionPointFullInfo>()
    fun observePointChanged() = pointChangedPublisher
}