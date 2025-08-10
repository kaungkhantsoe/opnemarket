package co.opntest.emarket.test

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

    val productModel = ProductModel(
        name = "product",
        price = 100.0,
        imageUrl = "https://example.com/image.png"
    )
}
