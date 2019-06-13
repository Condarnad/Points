package ru.test.points.model.partners

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import ru.test.points.model.common.Transformable
import java.util.*

data class PartnerDto(
    var id: String?,
    var name: String?,
    var picture: String?,
    var url: String?,
    var hasLocations: Boolean?,
    var isMomentary: Boolean?,
    var depositionDuration: String?,
    var limitations: String?,
    var pointType: String?,
    var description: String?

    ) : Transformable<Partner> {
    override fun transform() =
            Partner(
                id = id.orEmpty(),
                name = name,
                picture = picture,
                url = url,
                hasLocations = hasLocations,
                isMomentary = isMomentary,
                depositionDuration = depositionDuration,
                limitations = limitations,
                pointType = pointType,
                description = description
            )
}