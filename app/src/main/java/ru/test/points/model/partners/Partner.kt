package ru.test.points.model.partners

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Partner(
    @PrimaryKey var id: String = "",
    var name: String? = null,
    var picture: String? = null,
    var url: String? = null,
    var hasLocations: Boolean? = null,
    var isMomentary: Boolean? = null,
    var depositionDuration: String? = null,
    var limitations: String? = null,
    var pointType: String? = null,
    var description: String? = null
) : RealmObject()