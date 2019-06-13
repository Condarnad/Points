package ru.test.points.repository.points.db

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import org.junit.runner.RunWith
import ru.test.points.base.dagger.db.AppDatabase
import ru.test.points.model.points.DepositionPoint
import ru.test.points.model.points.DepositionPointInfo


@RunWith(AndroidJUnit4ClassRunner::class)
class PointsDaoTest {

    val room by lazy {
        Room.databaseBuilder(
            androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java, "app-db"
        ).fallbackToDestructiveMigration().build()
    }
    val pointsDao by lazy {
        room.pointsDao
    }
    val pointsInfoDao by lazy {
        room.pointsInfoDao
    }

    @Test
    fun insertPoints() {

        pointsDao.insertAll(listOf(DepositionPoint("123")))
    }

    @Test
    fun getPointById() {
    }

    @Test
    fun getPointInfoById() {
    }
}