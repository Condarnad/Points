package ru.test.points.model.common

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import io.realm.RealmObject

open class GeoPoint(
    var latitude: Double = .0,
    var longitude: Double = .0
) : RealmObject()  {
    fun toLatLng() = LatLng(latitude, longitude)

    constructor(latLng: LatLng) : this(latLng.latitude, latLng.longitude)

    fun distanceBetween(geoPoint: GeoPoint): Float {
        var distanceResults = FloatArray(1)
        Location.distanceBetween(
            this.latitude,
            this.longitude,
            geoPoint.latitude,
            geoPoint.longitude,
            distanceResults
        )
        return distanceResults[0]
    }

}