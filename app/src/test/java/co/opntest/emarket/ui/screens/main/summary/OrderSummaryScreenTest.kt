package co.opntest.emarket.ui.screens.main.summary

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import co.opntest.emarket.R
import co.opntest.emarket.domain.usecases.PlaceOrderUseCase
import co.opntest.emarket.test.MockUtil
import co.opntest.emarket.ui.models.toUiModel
import co.opntest.emarket.ui.screens.BaseScreenTest
import co.opntest.emarket.ui.screens.MainActivity
import co.opntest.emarket.ui.theme.ComposeTheme
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class OrderSummaryScreenTest : BaseScreenTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val mockPlaceOrderUseCase: PlaceOrderUseCase = mockk()

    private lateinit var viewModel: OrderSummaryViewModel
    private val productList = listOf(MockUtil.productModel.toUiModel().copy(selectedCount = 2))

    @Before
    fun setUp() {
        every { mockPlaceOrderUseCase(any()) } returns flowOf(Unit)
    }

    @Test
    fun `When entering the Order Summary screen, it shows the correct UI`() {
        setStandardTestDispatcher()
        initComposable {
            composeRule.waitForIdle()
            advanceUntilIdle()

            viewModel.setProducts(productList)

            onNodeWithText(
                activity.getString(
                    R.string.product_count_and_name,
                    productList.first().selectedCount,
                    productList.first().name
                )
            ).assertIsDisplayed()
            onNodeWithText(
                activity.getString(
                    R.string.price,
                    productList.first().price
                )
            ).assertIsDisplayed()
            onNodeWithText(activity.getString(R.string.order_summary)).assertIsDisplayed()
            onNodeWithText(
                activity.getString(
                    R.string.product_total_price,
                    viewModel.getTotalPrice()
                )
            ).assertIsDisplayed()
        }
    }

    @Test
    fun `When entering the Order Summary screen, place order button is enabled`() {
        setStandardTestDispatcher()
        initComposable {
            composeRule.waitForIdle()
            advanceUntilIdle()

            viewModel.setProducts(productList)

            onNodeWithTag(activity.getString(R.string.place_order_button)).assertIsEnabled()
        }
    }

    private fun initComposable(
        testBody: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.() -> Unit,
    ) {
        initViewModel()

        composeRule.activity.setContent {
            ComposeTheme {
                OrderSummaryScreen(
                    productList = emptyList(),
                    viewModel = viewModel,
                )
            }
        }
        testBody(composeRule)
    }

    private fun initViewModel() {
        viewModel = OrderSummaryViewModel(
            coroutinesRule.testDispatcherProvider,
            mockPlaceOrderUseCase
        )
    }
}
