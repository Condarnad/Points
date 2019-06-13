package ru.test.points.model.partners

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
@Parcelize
data class Partner(
    @PrimaryKey
    var id: String,
    var name: String? = null,
    var picture: String? = null,
    var url: String? = null,
    var hasLocations: Boolean? = null,
    var isMomentary: Boolean? = null,
    var depositionDuration: String? = null,
    var limitations: String? = null,
    var pointType: String? = null,
    var description: String? = null,

    @ColumnInfo(name = "partner_instantiatedAt")
    var instantiatedAt: Long? = Calendar.getInstance().timeInMillis
) : Parcelable