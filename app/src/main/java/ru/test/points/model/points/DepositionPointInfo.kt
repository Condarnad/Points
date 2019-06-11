package ru.test.points.model.points

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class DepositionPointInfo(
    @PrimaryKey var depositionPointExternalId: String = "",
    var isVisited: Boolean = false
): Parcelable