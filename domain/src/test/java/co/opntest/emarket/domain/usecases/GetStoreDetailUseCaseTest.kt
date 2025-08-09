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

class GetStoreDetailUseCaseTest {

    private lateinit var mockRepository: Repository
    private lateinit var useCase: GetStoreDetailUseCase

    @Before
    fun setUp() {
        mockRepository = mockk()
        useCase = GetStoreDetailUseCase(mockRepository)
    }

    @Test
    fun `When request successful, it returns success`() = runTest {
        val expected = MockUtil.storeDetail
        every { mockRepository.getStoreDetail() } returns flowOf(expected)

        useCase().collect {
            it shouldBe expected
        }
    }

    @Test
    fun `When request failed, it returns error`() = runTest {
        val expected = Exception()
        every { mockRepository.getStoreDetail() } returns flow { throw expected }

        useCase().catch {
            it shouldBe expected
        }.collect()
    }
}
