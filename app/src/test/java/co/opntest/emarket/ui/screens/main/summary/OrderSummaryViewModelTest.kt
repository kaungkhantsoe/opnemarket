package co.opntest.emarket.ui.screens.main.summary

import app.cash.turbine.test
import co.opntest.emarket.domain.usecases.PlaceOrderUseCase
import co.opntest.emarket.test.CoroutineTestRule
import co.opntest.emarket.test.MockUtil
import co.opntest.emarket.ui.models.toUiModel
import co.opntest.emarket.util.DispatchersProvider
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OrderSummaryViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutineTestRule()

    private val mockPlaceOrderUseCase: PlaceOrderUseCase = mockk()

    private lateinit var viewModel: OrderSummaryViewModel

    @Before
    fun setUp() {
        every { mockPlaceOrderUseCase(any()) } returns flowOf(Unit)
    }

    @Test
    fun `When calling setProducts, it updates products`() = runTest {
        val expected = listOf(MockUtil.productModel.toUiModel())
        initViewModel()

        viewModel.setProducts(expected)

        viewModel.products.test {
            expectMostRecentItem() shouldBe expected
        }
    }

    @Test
    fun `When calling getTotalPrice, it calculates the total price correctly`() = runTest {
        initViewModel()

        val products = listOf(MockUtil.productModel.toUiModel())
        viewModel.setProducts(products)

        val expectedTotalPrice = products.sumOf { it.price * it.selectedCount }
        val totalPrice = viewModel.getTotalPrice()

        totalPrice shouldBe expectedTotalPrice
    }

    @Test
    fun `When placing order successfully, it returns nothing`() = runTest {
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)
        val products = listOf(MockUtil.productModel.toUiModel())
        viewModel.setProducts(products)

        viewModel.placeOrder("Address")

        viewModel.onPlaceOrderSuccess.test {
            awaitItem() shouldBe Unit
        }
    }

    @Test
    fun `When placing store detail failed, it shows the corresponding error`() = runTest {
        val error = Exception()
        every { mockPlaceOrderUseCase(any()) } returns flow { throw error }
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        val products = listOf(MockUtil.productModel.toUiModel())
        viewModel.setProducts(products)
        viewModel.placeOrder("Address")

        viewModel.error.test {
            awaitItem() shouldBe error
        }
    }

    private fun initViewModel(dispatchers: DispatchersProvider = coroutinesRule.testDispatcherProvider) {
        viewModel = OrderSummaryViewModel(
            dispatchers,
            mockPlaceOrderUseCase
        )
    }
}
