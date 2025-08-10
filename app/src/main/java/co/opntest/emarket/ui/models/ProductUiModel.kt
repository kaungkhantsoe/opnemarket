package co.opntest.emarket.ui.models

import co.opntest.emarket.domain.models.ProductModel

data class ProductUiModel(
    val name: String,
    val price: Double,
    val imageUrl: String,
    var selectedCount: Int = 0,
)

fun ProductModel.toUiModel() = ProductUiModel(
    name = name.orEmpty(),
    price = price ?: 0.0,
    imageUrl = imageUrl.orEmpty(),
)

fun List<ProductModel>.toUiModelList() = map { it.toUiModel() }
