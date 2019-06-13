package ru.test.points.model.common

import android.location.Location
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GeoPoint(
    var latitude: Double = .0,
    var longitude: Double = .0
) : Parcelable {
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

data class GeoPointDto(
    var latitude: Double?,
    var longitude: Double?
) : Transformable<GeoPoint> {
    override fun transform() =
        GeoPoint(
            latitude = latitude ?: .0,
            longitude = longitude ?: .0
        )
}