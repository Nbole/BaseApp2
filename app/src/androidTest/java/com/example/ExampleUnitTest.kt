package com.example

import androidx.lifecycle.liveData
import com.example.baseapp.domain.usecase.MovieUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class MovieViewModel {
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()
    @RelaxedMockK
    private lateinit var getSuggestedSearchUseCase: MovieUseCase


}
/*
class SuggestedViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var getSuggestedSearchUseCase: GetSuggestedSearchUseCase

    @RelaxedMockK
    private lateinit var userAuthUseCase: UserAuthUseCase

    @RelaxedMockK
    private lateinit var totalItemsAmountUseCase: TotalItemsAmountUseCase

    @RelaxedMockK
    private lateinit var suggestedSearchDisplayMapper: SuggestedSearchDisplayMapper

    private lateinit var suggestedViewModel: SuggestedViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        suggestedViewModel = SuggestedViewModel(
            getSuggestedSearchUseCase = getSuggestedSearchUseCase,
            suggestedSearchDisplayMapper = suggestedSearchDisplayMapper,
            userAuthUseCase = userAuthUseCase,
            totalItemsAmountUseCase = totalItemsAmountUseCase
        )
    }

    @Test
    fun `test fetch suggested success when user is logged`() = runTest {
        //Given
        val logged = true
        coEvery { getSuggestedSearchUseCase(getKeyword()) } returns flowOf(
            Response.Success(getSuggestedSearch())
        )
        `init mapper config`(logged)
        every {
            userAuthUseCase.userAuthState
        } returns liveData { emit(UserAuthState.UserLoggedIn) }
        suggestedViewModel.uiState.test {
            //When
            suggestedViewModel.setEvent(SuggestedContract.Event.OnSearch(getKeyword()))
            //Then
            assertEquals(
                awaitItem(),
                SuggestedContract.State(suggestedSearchDisplayState = BaseState.Idle)
            )
            assertEquals(
                awaitItem(),
                SuggestedContract.State(
                    suggestedSearchDisplayState = Success(getSuggestedSearchDisplay(logged))
                )
            )
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { getSuggestedSearchUseCase(getKeyword()) }
    }

    private fun `init mapper config`(logged: Boolean) {
        every {
            suggestedSearchDisplayMapper.transform(getSuggestedSearch(), logged)
        } answers { getSuggestedSearchDisplay(logged) }
    }

    @Test
    fun `test fetch suggested empty when user is logged`() = runTest {
        //Given
        coEvery { getSuggestedSearchUseCase(getKeyword()) } returns flowOf(
            Response.Success(getEmptySuggestedSearch())
        )
        every { userAuthUseCase.userAuthState } returns liveData { emit(UserAuthState.UserLoggedIn) }
        suggestedViewModel.uiState.test {
            //When
            suggestedViewModel.setEvent(SuggestedContract.Event.OnSearch(getKeyword()))
            //Then
            assertEquals(awaitItem(), SuggestedContract.State(BaseState.Idle))
            assertEquals(awaitItem(), SuggestedContract.State(BaseState.Empty))
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { getSuggestedSearchUseCase(getKeyword()) }
    }

    @Test
    fun `test fetch suggested error when user is logged`() = runTest {
        //Given
        coEvery {
            getSuggestedSearchUseCase(getKeyword())
        } returns flowOf(Response.Failure(error = ResponseError.Default()))
        every { userAuthUseCase.userAuthState } returns liveData { emit(UserAuthState.UserLoggedIn) }
        suggestedViewModel.uiState.test {
            //When
            suggestedViewModel.setEvent(SuggestedContract.Event.OnSearch(getKeyword()))
            //Then
            assertEquals(awaitItem(), SuggestedContract.State(BaseState.Idle))
            assertEquals(awaitItem(), SuggestedContract.State(BaseState.Error.Default))
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { getSuggestedSearchUseCase(getKeyword()) }
    }

    @Test
    fun `test fetch suggested success when user is guest`() = runTest {
        //Given
        val logged = false
        `init mapper config`(logged)
        coEvery {
            getSuggestedSearchUseCase(
                keyword = getKeyword(),
                latitude = getLatitude(),
                longitude = getLongitude()
            )
        } returns flowOf(Response.Success(getSuggestedSearch()))
        every {
            userAuthUseCase.userAuthState
        } returns liveData { emit(GuestUser(getCoordinates())) }
        suggestedViewModel.uiState.test {
            //When
            suggestedViewModel.setEvent(SuggestedContract.Event.OnSearch(getKeyword()))
            //Then
            assertEquals(awaitItem(), SuggestedContract.State(BaseState.Idle))
            val expected = awaitItem()
            assertEquals(
                expected,
                SuggestedContract.State(
                    suggestedSearchDisplayState = Success(getSuggestedSearchDisplay(logged))
                )
            )
            assertEquals(
                (expected.suggestedSearchDisplayState as Success<SuggestedSearchDisplay>).data,
                getSuggestedSearchDisplay(logged)
            )
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { getSuggestedSearchUseCase(getKeyword(), getLatitude(), getLongitude()) }
    }

    @Test
    fun `test fetch suggested empty when user is guest`() = runTest {
        //Given
        coEvery {
            getSuggestedSearchUseCase(
                keyword = getKeyword(),
                latitude = getLatitude(),
                longitude = getLongitude()
            )
        } returns flowOf(Response.Success(getEmptySuggestedSearch()))
        every { userAuthUseCase.userAuthState } returns liveData {
            emit(GuestUser(getCoordinates()))
        }
        suggestedViewModel.uiState.test {
            //When
            suggestedViewModel.setEvent(SuggestedContract.Event.OnSearch(getKeyword()))
            //Then
            assertEquals(awaitItem(), SuggestedContract.State(BaseState.Idle))
            assertEquals(awaitItem(), SuggestedContract.State(BaseState.Empty))
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { getSuggestedSearchUseCase(getKeyword(), getLatitude(), getLongitude()) }
    }

    @Test
    fun `test fetch suggested error when user is guest`() = runTest {
        //Given
        coEvery {
            getSuggestedSearchUseCase(
                keyword = getKeyword(),
                latitude = getLatitude(),
                longitude = getLongitude()
            )
        } returns flowOf(Response.Failure(error = ResponseError.Default()))
        every { userAuthUseCase.userAuthState } returns liveData {
            emit(GuestUser(getCoordinates()))
        }
        suggestedViewModel.uiState.test {
            //When
            suggestedViewModel.setEvent(SuggestedContract.Event.OnSearch(getKeyword()))
            //Then
            assertEquals(awaitItem(), SuggestedContract.State(BaseState.Idle))
            assertEquals(awaitItem(), SuggestedContract.State(BaseState.Error.Default))
            cancelAndIgnoreRemainingEvents()
        }
        coVerify { getSuggestedSearchUseCase(getKeyword(), getLatitude(), getLongitude()) }
    }

    object SuggestedViewModelTestHolder {

        fun getKeyword() = "keyword"

        fun getLatitude() = 1.1

        fun getLongitude() = 1.2

        fun getCoordinates() = Coordinates(getLatitude(), getLongitude())

        fun getSuggestedSearch() = SuggestedSearch(
            productList = listOf(SuggestedSearchProduct(id = 0, "")),
            supplierList = listOf(SuggestedSearchSupplier(id = 0, name = "", avatar = null)),
            brandList = listOf(SuggestedSearchBrand(id = 0, name = "", logo = null)),
            categoryList = listOf(SuggestedSearchCategory(id = 0, name = ""))
        )

        fun getEmptySuggestedSearch() = SuggestedSearch(
            productList = listOf(),
            supplierList = listOf(),
            brandList = listOf(),
            categoryList = listOf()
        )

        fun getSuggestedSearchDisplay(isLogged: Boolean) = SuggestedSearchDisplay(
            isLogged = isLogged,
            suggestedSearchItemDisplayList = listOf(
                SuggestedSearchSupplierItemDisplay(
                    2,
                    "name2",
                    "icon2.url"
                ),
                DividerItemDisplay,
                SuggestedSearchProductItemDisplay(1, "name1"),
                DividerItemDisplay,
                SuggestedSearchBrandItemDisplay(
                    3,
                    "name3",
                    "icon3.url"
                ),
                DividerItemDisplay,
                SuggestedSearchCategoryItemDisplay(4, "name4"),
            )
        )
    }
} */

class MainCoroutineRule(
    private val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}