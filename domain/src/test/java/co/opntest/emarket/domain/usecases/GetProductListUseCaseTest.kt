package co.opntest.emarket.domain.usecases

import co.opntest.emarket.domain.repositories.Repository
import co.opntest.emarket.domain.test.MockUtil
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetProductListUseCaseTest {

    private lateinit var mockRepository: Repository
    private lateinit var useCase: GetProductListUseCase

    @Before
    fun setUp() {
        mockRepository = mockk()
        useCase = GetProductListUseCase(mockRepository)
    }

    @Test
    fun `When request successful, it returns success`() = runTest {
        val expected = listOf(MockUtil.product)
        every { mockRepository.getProductList() } returns flowOf(expected)

        useCase().collect {
            it shouldBe expected
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = Exception()
        every { mockRepository.getProductList() } returns flow { throw expected }

        useCase().catch {
            it shouldBe expected
        }.collect()
    }
}
