package ru.test.points.model.points

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.android.parcel.Parcelize
import ru.test.points.model.partners.Partner

@Parcelize
data class DepositionPointFullInfo(
    @Embedded
    val partner: Partner?,
    @Embedded
    val depositionPointInfo: DepositionPointInfo?,
    @Embedded
    val depositionPoint: DepositionPoint
) : Parcelable