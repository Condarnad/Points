package ru.test.points.repository.partners.db

import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import ru.test.points.model.partners.Partner
import ru.test.points.model.points.DepositionPoint
import java.util.*
import javax.inject.Singleton

class PartnersRealm {

    private var lastSync = 0
    private val PARTNERS_LIFESPAN = 10 * 60 * 1000

    private fun getRealm() =
        Realm.getInstance(
            RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        )

    private fun shouldUpdate() =
        Calendar.getInstance().time.time - lastSync > PARTNERS_LIFESPAN

    fun insertPartners(partners: List<Partner>) =
        getRealm().executeTransactionAsync {
            it.copyToRealmOrUpdate(partners)
        }

    fun getPartners() =
        Single.create<List<Partner>> {
            val realm = getRealm()
            it.onSuccess(
                getRealm()
                    .copyFromRealm(
                        realm.where<Partner>()
                            .findAll()
                    )
            )
            realm.close()
        }

    fun getPointInfo() {

    }

    fun getPartner(id: String) =
        Single.create<Partner> {

            val realm = getRealm()
            val partner = realm.where<Partner>().findFirst()?.let { realm.copyFromRealm(it) }
            realm.close()

            if (partner != null)
                it.onSuccess(partner)
        }

}