package com.aakash.assignmentcurrencyconverter.presentation.viewmodel

import com.aakash.assignmentcurrencyconverter.domain.model.ConvertedCurrency
import com.aakash.assignmentcurrencyconverter.domain.model.CurrencyData
import com.aakash.assignmentcurrencyconverter.domain.usecase.FetchCurrencyUseCase
import com.aakash.assignmentcurrencyconverter.presentation.composeScreens.intent.UpdateCurrency
import com.aakash.assignmentcurrencyconverter.presentation.composeScreens.intent.UpdateTextField
import com.aakash.assignmentcurrencyconverter.presentation.composeScreens.state.CurrencyScreenState
import com.aakash.assignmentcurrencyconverter.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK(relaxed = true)
    lateinit var fetchCurrencyUseCase: FetchCurrencyUseCase

    lateinit var subject: CurrencyViewModel

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
        mockkStatic(Dispatchers::class)

        coEvery { fetchCurrencyUseCase.invoke() } returns flowOf(
            currencyData
        )
        every { Dispatchers.IO } answers {UnconfinedTestDispatcher()}
        every { Dispatchers.Default } answers {UnconfinedTestDispatcher()}

        subject = CurrencyViewModel(fetchCurrencyUseCase)
    }

    @Test
    fun `entering inValid amount should show error`() = runTest {

        val testResults = mutableListOf<CurrencyScreenState>()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            subject.state.toList(testResults)
        }

        subject.handleIntent(UpdateTextField("99,"))

        assertEquals(3, testResults.size)

        //Initial state
        assertEquals(CurrencyScreenState(
            baseCurrencyType = currencyData.base,
            currencyShortList = listOf("abc","def","ghi")
        ), testResults[0])

        assertEquals(CurrencyScreenState(
            baseCurrencyType = currencyData.base,
            currencyShortList = listOf("abc","def","ghi"),
            currencyTextFieldValue = "99,"
        ), testResults[1])

        assertEquals(CurrencyScreenState(
            baseCurrencyType = currencyData.base,
            currencyShortList = listOf("abc","def","ghi"),
            currencyTextFieldValue = "99,",
            currencyTextFieldErrorPresent = true
        ), testResults[2])
    }

    @Test
    fun `entering valid amount should updated all currencies`() = runTest {

        val testResults = mutableListOf<CurrencyScreenState>()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            subject.state.toList(testResults)
        }


        subject.handleIntent(UpdateTextField("2"))

        assertEquals(4, testResults.size)

        //Initial state
        assertEquals(CurrencyScreenState(
            baseCurrencyType = currencyData.base,
            currencyShortList = listOf("abc","def","ghi")
        ), testResults[0])

        assertEquals(CurrencyScreenState(
            baseCurrencyType = currencyData.base,
            currencyShortList = listOf("abc","def","ghi"),
            currencyTextFieldValue = "2"
        ), testResults[1])

        assertEquals(CurrencyScreenState(
            baseCurrencyType = currencyData.base,
            currencyShortList = listOf("abc","def","ghi"),
            currencyTextFieldValue = "2",
            baseCurrencyValue = 2.0
        ), testResults[2])

        assertEquals(CurrencyScreenState(
            baseCurrencyType = currencyData.base,
            currencyShortList = listOf("abc","def","ghi"),
            currencyTextFieldValue = "2",
            baseCurrencyValue = 2.0,
            convertedCurrencyList = listOf(
                ConvertedCurrency(
                    value = 2.0,
                    type = "abc"
                ),
                ConvertedCurrency(
                    value = 5.0,
                    type = "def"
                ),
                ConvertedCurrency(
                    value = 10.0,
                    type = "ghi"
                )
            )
        ), testResults[3])
    }

    @Test
    fun `change in currency type should be updated in state only when amount field is empty`() = runTest {
        val testResults = mutableListOf<CurrencyScreenState>()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            subject.state.toList(testResults)
        }


        subject.handleIntent(UpdateCurrency("def"))

        assertEquals(2, testResults.size)

        //Initial state
        assertEquals(CurrencyScreenState(
            baseCurrencyType = currencyData.base,
            currencyShortList = listOf("abc","def","ghi")
        ), testResults[0])

        assertEquals(CurrencyScreenState(
            baseCurrencyType = "def",
            currencyShortList = listOf("abc","def","ghi"),
            currencyTextFieldValue = "",
        ), testResults[1])
    }

    @Test
    fun `change in currency type should updated all currencies when amount is available`() = runTest {
        //update amount
        subject.handleIntent(UpdateTextField("2"))

        val testResults = mutableListOf<CurrencyScreenState>()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            subject.state.toList(testResults)
        }


        subject.handleIntent(UpdateCurrency("def"))

        assertEquals(3, testResults.size)

        //Initial state
        assertEquals(CurrencyScreenState(
            baseCurrencyType = currencyData.base,
            currencyShortList = listOf("abc","def","ghi"),
            currencyTextFieldValue = "2",
            baseCurrencyValue = 2.0,
            convertedCurrencyList = listOf(
                ConvertedCurrency(
                    value = 2.0,
                    type = "abc"
                ),
                ConvertedCurrency(
                    value = 5.0,
                    type = "def"
                ),
                ConvertedCurrency(
                    value = 10.0,
                    type = "ghi"
                )
            )
        ), testResults[0])


        assertEquals(CurrencyScreenState(
            baseCurrencyType = "def",
            currencyShortList = listOf("abc","def","ghi"),
            currencyTextFieldValue = "2",
            baseCurrencyValue = 2.0,
            convertedCurrencyList = listOf(
                ConvertedCurrency(
                    value = 2.0,
                    type = "abc"
                ),
                ConvertedCurrency(
                    value = 5.0,
                    type = "def"
                ),
                ConvertedCurrency(
                    value = 10.0,
                    type = "ghi"
                )
            )
        ), testResults[1])

        assertEquals(CurrencyScreenState(
            baseCurrencyType = "def",
            currencyShortList = listOf("abc","def","ghi"),
            currencyTextFieldValue = "2",
            baseCurrencyValue = 2.0,
            convertedCurrencyList = listOf(
                ConvertedCurrency(
                    value = 0.8,
                    type = "abc"
                ),
                ConvertedCurrency(
                    value = 2.0,
                    type = "def"
                ),
                ConvertedCurrency(
                    value = 4.0,
                    type = "ghi"
                )
            )
        ), testResults[2])
    }
}