package ru.test.points.model.points

import ru.test.points.model.common.GeoPoint
import ru.test.points.model.common.GeoPointDto
import ru.test.points.model.common.Transformable

data class DepositionPointDto(
    var externalId: String?,
    var partnerName: String?,
    var location: GeoPointDto?,
    var workHours: String?,
    var phones: String?,
    var addressInfo: String?,
    var fullAddress: String?
) : Transformable<DepositionPoint>{
    override fun transform() =
            DepositionPoint(
                externalId = externalId.orEmpty(),
                partnerName = partnerName.orEmpty(),
                location = location?.transform(),
                workHours = workHours,
                phones = phones,
                addressInfo = addressInfo,
                fullAddress = fullAddress
            )
}
