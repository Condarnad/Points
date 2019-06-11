package ru.test.points.repository.partners

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.test.points.repository.partners.db.PartnersRealm
import ru.test.points.repository.partners.network.PartnersApi
import ru.test.points.repository.points.network.PointsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartnersRepository @Inject constructor(
    val partnersRealm: PartnersRealm,
    val parntersApi: PartnersApi
) {

    fun getPartners() =
        Single.concat(
            partnersRealm.getPartners(),
            parntersApi.getDepositionPartners(accountType = "Credit").observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    partnersRealm.insertPartners(it)
                }
        )


    fun getPartner(partnerName: String) = ""
}