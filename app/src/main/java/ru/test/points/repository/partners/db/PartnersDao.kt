package ru.test.points.repository.partners.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.test.points.model.partners.Partner
import ru.test.points.model.points.DepositionPoint
import java.util.*
import javax.inject.Singleton

@Dao
interface PartnersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(partners: List<Partner>)

    @Query("DELETE FROM partner WHERE partner_instantiatedAt<:time")
    fun deleteByTime(time: Long)
}