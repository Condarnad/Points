package ru.test.points.repository.points.db

import io.reactivex.Observable
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import ru.test.points.model.common.GeoPoint
import ru.test.points.model.common.GeoPointBounds
import ru.test.points.model.partners.Partner
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointExtended
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Singleton

@Singleton
class PointsRealm {

    private fun getRealm() =
        Realm.getInstance(
            RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        )

    private var lastSync = 0
    private val POINTS_LIFESPAN = 10 * 60 * 1000

    fun insertPoints(points: List<DepositionPoint>) =
        getRealm().executeTransaction {
            it.insertOrUpdate(points)
        }

    fun getPointsByRadius(geoPoint: GeoPoint, radius: Int): List<DepositionPointExtended> {
        lateinit var pointsExtended: List<DepositionPointExtended>

        val currentTime = Calendar.getInstance().timeInMillis

        getRealm().executeTransaction { realm ->
            val points = realm
                .copyFromRealm(realm.where<DepositionPointExtended>().findAll())
                .filter { it.instantiatedAt + POINTS_LIFESPAN > currentTime }
                .filter { (it.location?.distanceBetween(geoPoint)?.toInt() ?: Int.MAX_VALUE) < radius }
                .take(500)
            pointsExtended = points
        }
        return pointsExtended
    }

    fun getPointsByRadiusAsync(geoPoint: GeoPoint, radius: Int) =
        Single.create<List<DepositionPointExtended>> {
            it.onSuccess(getPointsByRadius(geoPoint, radius))
        }

    fun getPointById(id: String): DepositionPointExtended? {
        var found: DepositionPointExtended? = null
        getRealm().executeTransaction {
            found =
                it.where<DepositionPointExtended>()
                    .equalTo("externalId", id)
                    .findFirst()
                    ?.let(it::copyFromRealm)
        }
        return found
    }

    fun getPointByIdAsync(id: String) =
        Single.create<DepositionPointExtended> {
            val point = getPointById(id)
            if (point != null)
                it.onSuccess(point)
            else
                it.onError(IllegalArgumentException())
        }

    fun setPointVisitedStatus(id: String, visited: Boolean) {
        getRealm().executeTransaction {
            val foundPoint = it.where<DepositionPointExtended>()
                .equalTo("externalId", id)
                .findFirst()
            foundPoint?.apply {
                isVisited = visited
                it.insertOrUpdate(this)
            }
        }
    }

}