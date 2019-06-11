package ru.test.points.model.points

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import ru.test.points.model.common.GeoPoint
import java.util.*


@Entity
@Parcelize
data class DepositionPoint(
    @PrimaryKey
    var externalId: String = "",
    var partnerName: String = "",
    @Embedded var location: GeoPoint? = null,
    var workHours: String? = null,
    var phones: String? = null,
    var addressInfo: String? = null,
    var fullAddress: String? = null,

    @ColumnInfo(name = "point_instantiatedAt")
    var instantiatedAt: Long? = Calendar.getInstance().timeInMillis
) : Parcelable

