package ru.test.points.model.common

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

data class GeoPointBounds(
    val southwest: GeoPoint,
    val northeast: GeoPoint
) {
    fun toLatLngBounds() = LatLngBounds(southwest.toLatLng(), northeast.toLatLng())
    fun getCenter() =
        GeoPoint(
            (northeast.latitude+southwest.latitude)/2,
            (northeast.longitude+southwest.longitude)/2
        )

    constructor(latLng: LatLngBounds) : this(GeoPoint(latLng.southwest), GeoPoint(latLng.northeast))
}