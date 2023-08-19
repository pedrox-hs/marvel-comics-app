package com.example.comics.presentation

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
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ComicsViewModelTest {
    private val repository: ComicsRepository = mockk()
    private lateinit var viewModel: ComicsViewModel

    @get:Rule
    val coroutineRule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = ComicsViewModel(repository)
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
        val results = mutableListOf<ComicsViewState>()
        val job = launch(coroutineRule.dispatcher) { viewModel.state.toList(results) }

        // act
        runCurrent()

        // assert
        val expectedStates = listOf(
            ComicsViewState(isLoading = true),
            ComicsViewState(items = expectedItems()),
        )
        MatcherAssert.assertThat(results, CoreMatchers.equalTo(expectedStates))
        job.cancel()
    }

    @Test
    fun `when success then state should emit state with error`() = runTest {
        // arrange
        coEvery { repository.fetch() } throws Exception()
        val results = mutableListOf<ComicsViewState>()
        val job = launch { viewModel.state.toList(results) }

        // act
        runCurrent()

        // assert
        val expectedStates = listOf(
            ComicsViewState(isLoading = true),
            ComicsViewState(isError = true),
        )
        MatcherAssert.assertThat(results, CoreMatchers.equalTo(expectedStates))
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
    ComicsVO(
        id = "1",
        image = "image",
        title = "title",
        subtitle = "description"
    )
)