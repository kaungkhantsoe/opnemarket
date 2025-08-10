package co.opntest.emarket.domain.test

import co.opntest.emarket.domain.models.PlaceOrderModel
import co.opntest.emarket.domain.models.ProductModel
import co.opntest.emarket.domain.models.StoreDetailModel
import java.time.LocalTime

object MockUtil {

    val storeDetail = StoreDetailModel(
        name = "name",
        rating = 4.5,
        openingTime = LocalTime.of(15, 30, 45, 365000000),
        closingTime = LocalTime.of(19, 45, 51, 365000000)
    )

    val product = ProductModel(
        name = "name",
        price = 100.0,
        imageUrl = "https://example.com/image.jpg"
    )

    val placeOrder = PlaceOrderModel(
        products = listOf(product),
        deliveryAddress = "123 Main St, City, Country",
    )
}
