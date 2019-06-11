package ru.test.points.model.points

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ru.test.points.model.common.GeoPoint
import java.util.*


open class DepositionPoint(
    @PrimaryKey
    var externalId: String = "",
    var partnerName: String? = "",
    var location: GeoPoint? = null,
    var workHours: String? = null,
    var phones: String? = null,
    var addressInfo: String? = null,
    var fullAddress: String? = null,
    var instantiatedAt: Long = Calendar.getInstance().timeInMillis
) : RealmObject()

open class DepositionPointExtended(
    var isVisited: Boolean = false
) : DepositionPoint()
