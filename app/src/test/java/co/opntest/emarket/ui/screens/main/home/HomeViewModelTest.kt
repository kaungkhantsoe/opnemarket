package co.opntest.emarket.ui.screens.main.home

import app.cash.turbine.test
import co.opntest.emarket.domain.usecases.GetProductListUseCase
import co.opntest.emarket.domain.usecases.GetStoreDetailUseCase
import co.opntest.emarket.test.CoroutineTestRule
import co.opntest.emarket.test.MockUtil
import co.opntest.emarket.ui.models.toUiModel
import co.opntest.emarket.ui.models.toUiModelList
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

    private val mockGetStoreDetailUseCase: GetStoreDetailUseCase = mockk()
    private val mockGetProductListUseCase: GetProductListUseCase = mockk()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        every { mockGetStoreDetailUseCase() } returns flowOf(MockUtil.storeDetail)
        every { mockGetProductListUseCase() } returns flowOf(listOf(MockUtil.productModel))
    }

    @Test
    fun `When loading store detail successfully, it shows the store detail`() = runTest {
        initViewModel()

        viewModel.storeDetail.test {
            expectMostRecentItem() shouldBe MockUtil.storeDetail.toUiModel()
        }
    }

    @Test
    fun `When loading store detail failed, it shows the corresponding error`() = runTest {
        val error = Exception()
        every { mockGetStoreDetailUseCase() } returns flow { throw error }
        initViewModel(dispatchers = CoroutineTestRule(StandardTestDispatcher()).testDispatcherProvider)

        viewModel.error.test {
            advanceUntilIdle()

            expectMostRecentItem() shouldBe error
        }
    }

    @Test
    fun `When loading product list successfully, it shows the product list`() = runTest {
        val expected = listOf(MockUtil.productModel).toUiModelList()
        initViewModel()

        viewModel.products.test {
            expectMostRecentItem() shouldBe expected
        }
    }

    @Test
    fun `When updating product count, it updates the count of correct product`() = runTest {
        val expected = listOf(
            MockUtil.productModel,
            MockUtil.productModel.copy(name = "product2")
        )
        every { mockGetProductListUseCase() } returns flowOf(expected)
        initViewModel()

        viewModel.products.test {
            expectMostRecentItem().size shouldBe 2
        }

        viewModel.updateProductCount(2, MockUtil.productModel.toUiModel())

        viewModel.products.test {
            expectMostRecentItem().first().selectedCount shouldBe 2
        }
    }


    @Test
    fun `When loading product list failed, it shows the corresponding error`() = runTest {
        val error = Exception()
        every { mockGetProductListUseCase() } returns flow { throw error }
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
            awaitItem() shouldBe true
            awaitItem() shouldBe false
        }
    }

    private fun initViewModel(dispatchers: DispatchersProvider = coroutinesRule.testDispatcherProvider) {
        viewModel = HomeViewModel(
            dispatchers,
            mockGetStoreDetailUseCase,
            mockGetProductListUseCase
        )
    }
}
