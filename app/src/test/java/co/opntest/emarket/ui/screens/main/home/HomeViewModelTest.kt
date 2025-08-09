package co.opntest.emarket.ui.screens.main.home

import app.cash.turbine.test
import co.opntest.emarket.domain.usecases.UseCase
import co.opntest.emarket.test.CoroutineTestRule
import co.opntest.emarket.test.MockUtil
import co.opntest.emarket.ui.models.toUiModel
import co.opntest.emarket.util.DispatchersProvider
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutineTestRule()

    private val mockUseCase: UseCase = mockk()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        every { mockUseCase() } returns flowOf(MockUtil.models)

        initViewModel()
    }

    @Test
    fun `When loading models successfully, it shows the model list`() = runTest {
        viewModel.uiModels.test {
            expectMostRecentItem() shouldBe MockUtil.models.map { it.toUiModel() }
        }
    }

    @Test
    fun `When loading models failed, it shows the corresponding error`() = runTest {
        val error = Exception()
        every { mockUseCase() } returns flow { throw error }
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.error.test {
            advanceUntilIdle()

            expectMostRecentItem() shouldBe error
        }
    }

    @Test
    fun `When loading models, it shows and hides loading correctly`() = runTest {
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.isLoading.test {
            awaitItem() shouldBe false
            awaitItem() shouldBe true
            awaitItem() shouldBe false
        }
    }

    private fun initViewModel(dispatchers: DispatchersProvider = coroutinesRule.testDispatcherProvider) {
        viewModel = HomeViewModel(
            dispatchers,
            mockUseCase
        )
    }
}
