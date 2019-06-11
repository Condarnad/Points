package ru.test.points.repository.partners

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.test.points.model.partners.Partner
import ru.test.points.repository.partners.db.PartnersDao
import ru.test.points.repository.partners.network.PartnersApi
import ru.test.points.repository.points.network.PointsApi
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartnersRepository @Inject constructor(
    val partnersDao: PartnersDao,
    val parntersApi: PartnersApi
) {

    private val CACHE_TIME = 10 * 60 * 1000

    private fun getPartners() =
        parntersApi.getDepositionPartners("Credit")
            .doOnSuccess {
                partnersDao.insertAll(it)
            }

    fun syncPartners() =
        clearCache()
            .andThen(getPartners())
            //.onErrorResumeNext(getPartnerSync())

    private fun getFromServerById(partnerId: String) =
        getPartners().map {
            it.find { it.id == partnerId }
        }.toFlowable()

    private fun clearCache() =
        Completable.fromAction {
            partnersDao.deleteByTime(Calendar.getInstance().timeInMillis - CACHE_TIME)
        }

    private fun getPartnerSync(partnerId: String) =
        partnersDao.getPartnerByIdSync(partnerId)

    /*fun getPartner(partnerId: String) =
        clearCache()
            .andThen(
                Flowable.fromCallable { getPartnerSync(partnerId) }
            )
            .onErrorResumeNext(getFromServerById(partnerId))*/

}