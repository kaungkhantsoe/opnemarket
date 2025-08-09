package co.opntest.emarket.ui.screens.main.home

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import co.opntest.emarket.R
import co.opntest.emarket.domain.usecases.GetStoreDetailUseCase
import co.opntest.emarket.test.MockUtil
import co.opntest.emarket.ui.models.toUiModel
import co.opntest.emarket.ui.screens.BaseScreenTest
import co.opntest.emarket.ui.screens.MainActivity
import co.opntest.emarket.ui.theme.ComposeTheme
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeScreenTest : BaseScreenTest() {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val mockGetStoreDetailUseCase: GetStoreDetailUseCase = mockk()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        every { mockGetStoreDetailUseCase() } returns flowOf(MockUtil.storeDetail)
    }

    @Test
    fun `When entering the Home screen and loading the store detail successfully, it shows store detail`() {
        setStandardTestDispatcher()
        val storeDetail = MockUtil.storeDetail.toUiModel()
        initComposable {
            advanceUntilIdle()

            onNodeWithText(storeDetail.name).assertIsDisplayed()
            onNodeWithText("(${storeDetail.rating})").assertIsDisplayed()
            onNodeWithText(
                activity.getString(
                    R.string.open_and_close_time,
                    storeDetail.openingTime,
                    storeDetail.closingTime
                )
            ).assertIsDisplayed()
        }
    }

    @Test
    fun `When entering the Home screen and loading the store detail successfully but if there is no opening hour or closing hour, it does not show the information`() {
        setStandardTestDispatcher()
        val storeDetail = MockUtil.storeDetail
            .copy(openingTime = null, closingTime = null)
        every { mockGetStoreDetailUseCase() } returns flowOf(storeDetail)

        val storeDetailUiModel = storeDetail.toUiModel()

        initComposable {
            advanceUntilIdle()

            onNodeWithText(storeDetailUiModel.name).assertIsDisplayed()
            onNodeWithText("(${storeDetailUiModel.rating})").assertIsDisplayed()
            onNodeWithText(
                activity.getString(
                    R.string.open_and_close_time,
                    storeDetailUiModel.openingTime,
                    storeDetailUiModel.closingTime
                )
            ).assertIsNotDisplayed()
        }
    }

    @Test
    fun `When entering the Home screen and loading the store detail fails, it shows error screen`() {
        setStandardTestDispatcher()

        val error = Exception()
        every { mockGetStoreDetailUseCase() } returns flow { throw error }

        initComposable {
            composeRule.waitForIdle()
            advanceUntilIdle()

            onNodeWithText(activity.getString(R.string.something_went_wrong)).assertIsDisplayed()
        }
    }

    private fun initComposable(
        testBody: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>.() -> Unit,
    ) {
        initViewModel()

        composeRule.activity.setContent {
            ComposeTheme {
                HomeScreen(
                    viewModel = viewModel,
                )
            }
        }
        testBody(composeRule)
    }

    private fun initViewModel() {
        viewModel = HomeViewModel(
            coroutinesRule.testDispatcherProvider,
            mockGetStoreDetailUseCase,
        )
    }
}
