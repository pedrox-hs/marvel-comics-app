package com.example.comics.view

import com.example.comics.CoroutinesTestRule
import com.example.comics.domain.entity.Comic
import com.example.comics.domain.repository.ComicsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val repository: ComicsRepository = mockk()
    private lateinit var viewModel: MainViewModel

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `when init then repository is called`() = runTest {
        // arrange
        coEvery { repository.fetch() } returns emptyList()

        // act
        viewModel.state
        runCurrent()

        // assert
        coVerify(exactly = 1) { repository.fetch() }
        confirmVerified(repository)
    }

    @Test
    fun `when refresh then repository is called`() = runTest {
        // arrange
        coEvery { repository.fetch() } returns emptyList()

        // act
        viewModel.refresh()
        runCurrent()

        // assert
        coVerify(exactly = 1) { repository.fetch() }
        confirmVerified(repository)
    }

    @Test
    fun `when success then state should emit state with items`() = runTest {
        // arrange
        coEvery { repository.fetch() } returns comics()
        val results = mutableListOf<MainViewState>()
        val job = launch(coroutineRule.dispatcher) { viewModel.state.toList(results) }

        // act
        runCurrent()

        // assert
        val expectedStates = listOf(
            MainViewState(isLoading = true),
            MainViewState(items = expectedItems()),
        )
        assertThat(results, equalTo(expectedStates))
        job.cancel()
    }

    @Test
    fun `when success then state should emit state with error`() = runTest {
        // arrange
        coEvery { repository.fetch() } throws Exception()
        val results = mutableListOf<MainViewState>()
        val job = launch { viewModel.state.toList(results) }

        // act
        runCurrent()

        // assert
        val expectedStates = listOf(
            MainViewState(isLoading = true),
            MainViewState(isError = true),
        )
        assertThat(results, equalTo(expectedStates))
        job.cancel()
    }
}

private fun comics() = listOf(
    Comic(
        id = "1",
        image = "image",
        title = "title",
        description = "description"
    )
)

private fun expectedItems() = listOf(
    ItemVO(
        id = "1",
        image = "image",
        title = "title",
        subtitle = "description"
    )
)