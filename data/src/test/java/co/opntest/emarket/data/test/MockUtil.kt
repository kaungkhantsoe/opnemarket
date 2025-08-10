package co.opntest.emarket.data.test

import co.opntest.emarket.data.remote.models.responses.ErrorResponse
import co.opntest.emarket.data.remote.models.responses.ProductResponse
import co.opntest.emarket.data.remote.models.responses.StoreDetailResponse
import co.opntest.emarket.domain.models.PlaceOrderModel
import co.opntest.emarket.domain.models.ProductModel
import io.mockk.every
import io.mockk.mockk
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.time.LocalTime

object MockUtil {

    val mockHttpException: HttpException
        get() {
            val response = mockk<Response<Any>>()
            val httpException = mockk<HttpException>()
            val responseBody = mockk<ResponseBody>()
            every { response.code() } returns 500
            every { response.message() } returns "message"
            every { response.errorBody() } returns responseBody
            every { httpException.code() } returns response.code()
            every { httpException.message() } returns response.message()
            every { httpException.response() } returns response
            every { responseBody.string() } returns "{\n" +
                    "  \"message\": \"message\"\n" +
                    "}"
            return httpException
        }

    val errorResponse = ErrorResponse(
        message = "message"
    )

    val storeDetailResponse = StoreDetailResponse(
        name = "Store",
        rating = 4.6,
        openingTime = LocalTime.of(15, 30, 45, 365000000),
        closingTime = LocalTime.of(19, 45, 51, 365000000)
    )

    val productResponse = ProductResponse(
        name = "Product",
        price = 100.0,
        imageUrl = "https://example.com/image.png"
    )

    val placeOrderModel = PlaceOrderModel(
        products = listOf(
            ProductModel(
                name = "Product",
                price = 100.0,
                imageUrl = "https://example.com/image.png"
            )
        ),
        deliveryAddress = "123 Main St, City, Country"
    )
}
