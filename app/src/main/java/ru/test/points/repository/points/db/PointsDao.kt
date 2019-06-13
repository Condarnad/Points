package ru.test.points.repository.points.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ru.test.points.model.common.GeoPoint
import ru.test.points.model.common.GeoPointBounds
import ru.test.points.model.partners.Partner
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointFullInfo
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Singleton

@Dao
interface PointsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(depositionPoints: List<DepositionPoint>)

    @Query("DELETE FROM depositionPoint WHERE point_instantiatedAt<:time")
    fun deleteByTime(time: Long)

    @Query(
        """
        SELECT depositionPoint.*, partner.*, depositionpointinfo.*
        FROM depositionPoint
        LEFT JOIN partner
            ON partner.id = partnerName
        LEFT JOIN depositionpointinfo
            ON depositionpointinfo.depositionPointExternalId = externalId
        WHERE
            (latitude BETWEEN :southeastLat AND :northeastLat)
            AND (longitude BETWEEN :southeastLon AND :northeastLon)
        LIMIT 500
        """
    )
    fun getAllInBoundsFullInfo(
        northeastLat: Double,
        northeastLon: Double,
        southeastLat: Double,
        southeastLon: Double
    ): Single<List<DepositionPointFullInfo>>

    @Query(
        """
        SELECT depositionPoint.*, partner.*, depositionpointinfo.*
        FROM depositionPoint
        LEFT JOIN partner
            ON partner.id = partnerName
        LEFT JOIN depositionpointinfo
            ON depositionpointinfo.depositionPointExternalId = externalId
        WHERE
            externalId = :externalId
        LIMIT 1
        """
    )
    fun getPointFullInfo(
        externalId: String
    ): Flowable<DepositionPointFullInfo>
}