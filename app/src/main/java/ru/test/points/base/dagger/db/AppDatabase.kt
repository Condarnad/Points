package ru.test.points.base.dagger.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.test.points.model.partners.Partner
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointInfo
import ru.test.points.repository.partners.db.PartnersDao
import ru.test.points.repository.points.db.PointsDao
import ru.test.points.repository.points.db.PointsInfoDao
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        DepositionPoint::class,
        DepositionPointInfo::class,
        Partner::class
    ],
    version = 8
)
abstract class AppDatabase : RoomDatabase() {
    abstract val pointsDao: PointsDao
    abstract val pointsInfoDao: PointsInfoDao
    abstract val partnersDao: PartnersDao
}