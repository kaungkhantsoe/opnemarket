package co.opntest.emarket.ui.models

import co.opntest.emarket.domain.models.StoreDetailModel
import co.opntest.emarket.extensions.formatTo12HourString

data class StoreDetailUiModel(
    val name: String,
    val rating: Double,
    val openingTime: String,
    val closingTime: String,
)

fun StoreDetailModel.toUiModel() = StoreDetailUiModel(
    name = name.orEmpty(),
    rating = rating ?: 0.0,
    openingTime = openingTime?.formatTo12HourString().orEmpty(),
    closingTime = closingTime?.formatTo12HourString().orEmpty(),
)
