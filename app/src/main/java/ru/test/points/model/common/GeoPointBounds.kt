package ru.test.points.model.common

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

data class GeoPointBounds(
    val southwest: GeoPoint,
    val northeast: GeoPoint
) {
    fun toLatLngBounds() = LatLngBounds(southwest.toLatLng(), northeast.toLatLng())

    constructor(latLng: LatLngBounds) : this(GeoPoint(latLng.southwest), GeoPoint(latLng.northeast))
}