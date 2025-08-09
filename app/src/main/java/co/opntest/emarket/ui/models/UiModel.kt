package co.opntest.emarket.ui.models

import co.opntest.emarket.domain.models.Model

data class UiModel(
    val id: Int
)

fun Model.toUiModel() = UiModel(id = id ?: -1)
