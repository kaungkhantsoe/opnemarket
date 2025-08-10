package co.opntest.emarket.ui.models

data class ProductUiModel(
    val name: String,
    val price: Double,
    val imageUrl: String,
    var selectedCount: Int = 0,
)
