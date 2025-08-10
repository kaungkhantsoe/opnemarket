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

class PlaceOrderUseCaseTest {
    private lateinit var mockRepository: Repository
    private lateinit var useCase: PlaceOrderUseCase

    @Before
    fun setUp() {
        mockRepository = mockk()
        useCase = PlaceOrderUseCase(mockRepository)
    }

    @Test
    fun `When request successful, it returns nothing`() = runTest {
        every { mockRepository.placeOrder(any()) } returns flowOf(Unit)

        useCase(MockUtil.placeOrder).collect {
            it shouldBe Unit
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = Exception()
        every { mockRepository.placeOrder(any()) } returns flow { throw expected }

        useCase(MockUtil.placeOrder).catch {
            it shouldBe expected
        }.collect()
    }
}
