package com.aakash.assignmentcurrencyconverter.domain.usecaseImpl

import com.aakash.assignmentcurrencyconverter.domain.model.CurrencyData
import com.aakash.assignmentcurrencyconverter.domain.repository.databaseRepository.CurrencyDatabaseRepo
import com.aakash.assignmentcurrencyconverter.domain.repository.networkRepository.CurrencyNetworkRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class FetchCurrencyUseCaseImplTest {

    @MockK(relaxed = true)
    lateinit var mockCurrencyNetworkRepo: CurrencyNetworkRepo

    @MockK(relaxed = true)
    lateinit var mockCurrencyDatabaseRepo: CurrencyDatabaseRepo

    lateinit var subject: FetchCurrencyUseCaseImpl

    val currencyData = CurrencyData(
        disclaimer = "",
        license = "",
        base = "abc",
        timestamp = System.currentTimeMillis(),
        savedTimeStamp = System.currentTimeMillis(),
        rates = hashMapOf(
            "abc" to 1.0,
            "def" to 2.5,
            "ghi" to 5.0
        )
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        subject = FetchCurrencyUseCaseImpl(mockCurrencyNetworkRepo, mockCurrencyDatabaseRepo)
    }

    @Test
    fun `valid data in database should be returned`() = runTest {
        coEvery { mockCurrencyDatabaseRepo.getCurrencyData() } returns flowOf(
            currencyData
        )
        val data = subject.invoke().first()
        assertEquals(currencyData, data)
    }

    @Test
    fun `fetch data from server and save in database if data is not available in database`() = runTest {
        coEvery { mockCurrencyDatabaseRepo.getCurrencyData() } returns flowOf(
            null
        )
        coEvery { mockCurrencyNetworkRepo.getCurrencyDataFromApi() } returns Response.success(currencyData)
        subject.invoke().collect {  }

        coVerify { mockCurrencyNetworkRepo.getCurrencyDataFromApi() }
        coVerify { mockCurrencyDatabaseRepo.saveCurrencyData(currencyData) }
    }

    @Test
    fun `delete data from database if data has passed refresh interval`() = runTest {
        val expiredCurrencyData = CurrencyData(
            disclaimer = "",
            license = "",
            base = "abc",
            timestamp = 100000,
            savedTimeStamp = 100000,
            rates = hashMapOf(
                "abc" to 1.0
            )
        )

        coEvery { mockCurrencyDatabaseRepo.getCurrencyData() } returns flowOf(
            expiredCurrencyData
        )

        subject.invoke().collect {  }
        coVerify { mockCurrencyDatabaseRepo.deleteCurrencyData() }
    }

}