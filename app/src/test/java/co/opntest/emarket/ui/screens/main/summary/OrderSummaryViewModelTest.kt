package co.opntest.emarket.ui.screens.main.summary

import app.cash.turbine.test
import co.opntest.emarket.test.MockUtil
import co.opntest.emarket.ui.models.toUiModel
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class OrderSummaryViewModelTest {

    private lateinit var viewModel: OrderSummaryViewModel

    @Before
    fun setUp() {
        viewModel = OrderSummaryViewModel()
    }

    @Test
    fun `When calling setProducts, it updates products`() = runTest {
        val expected = listOf(MockUtil.productModel.toUiModel())

        viewModel.setProducts(expected)

        viewModel.products.test {
            expectMostRecentItem() shouldBe expected
        }
    }

    @Test
    fun `When calling getTotalPrice, it calculates the total price correctly`() = runTest {
        val products = listOf(MockUtil.productModel.toUiModel())
        viewModel.setProducts(products)

        val expectedTotalPrice = products.sumOf { it.price * it.selectedCount }
        val totalPrice = viewModel.getTotalPrice()

        totalPrice shouldBe expectedTotalPrice
    }
}
