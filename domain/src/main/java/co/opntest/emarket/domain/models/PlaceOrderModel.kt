package co.opntest.emarket.domain.models

data class PlaceOrderModel(
    val products: List<ProductModel>,
    val deliveryAddress: String,
)
