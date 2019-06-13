package ru.test.points.repository.points.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ru.test.points.model.common.GeoPoint
import ru.test.points.model.common.GeoPointBounds
import ru.test.points.model.partners.Partner
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointInfo
import java.lang.IllegalArgumentException
import java.util.*
import javax.inject.Singleton

@Dao
interface PointsInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(depositionPoint: DepositionPointInfo): Completable
}