package co.opntest.emarket.domain.models

import java.time.LocalTime

data class StoreDetailModel(
    val name: String?,
    val rating: Double?,
    val openingTime: LocalTime?,
    val closingTime: LocalTime?
)
