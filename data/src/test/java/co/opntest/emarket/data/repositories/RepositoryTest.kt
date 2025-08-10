package co.opntest.emarket.data.repositories

import co.opntest.emarket.data.remote.models.responses.toModel
import co.opntest.emarket.data.remote.models.responses.toModelList
import co.opntest.emarket.data.remote.services.ApiService
import co.opntest.emarket.data.test.MockUtil
import co.opntest.emarket.domain.repositories.Repository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryTest {

    private lateinit var mockService: ApiService
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        mockService = mockk()
        repository = RepositoryImpl(mockService)
    }

    @Test
    fun `When calling getStoreDetail successful, it returns success`() = runTest {
        val expected = MockUtil.storeDetailResponse
        coEvery { mockService.getStoreDetail() } returns expected

        repository.getStoreDetail().collect {
            it shouldBe expected.toModel()
        }
    }

    @Test
    fun `When calling getStoreDetail failed, it returns error`() = runTest {
        val expected = Throwable()
        coEvery { mockService.getStoreDetail() } throws expected

        repository.getStoreDetail().catch {
            it shouldBe expected
        }.collect()
    }

    @Test
    fun `When calling getProductList successful, it returns success`() = runTest {
        val expected = listOf(MockUtil.productResponse)
        coEvery { mockService.getProductList() } returns expected

        repository.getProductList().collect {
            it shouldBe expected.toModelList()
        }
    }

    @Test
    fun `When calling getProductList failed, it returns error`() = runTest {
        val expected = Throwable()
        coEvery { mockService.getProductList() } throws expected

        repository.getProductList().catch {
            it shouldBe expected
        }.collect()
    }
}
