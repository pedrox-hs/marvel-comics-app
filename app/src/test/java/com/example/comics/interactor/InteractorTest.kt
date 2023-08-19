package com.example.comics.interactor

import com.example.comics.CoroutinesTestRule
import com.example.comics.domain.entity.Comic
import com.example.comics.domain.repository.ComicsRepository
import com.example.comics.presenter.IPresenter
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class InteractorTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private lateinit var interactor: Interactor

    private val iPresenter: IPresenter = mockk(relaxed = true)
    private val repository: ComicsRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        interactor = Interactor(iPresenter, repository)
    }

    @Test
    fun `when execute interactor getComics call repository`() = runBlocking {
        // arrange
        coEvery { repository.fetch() } returns listOf()

        // act
        interactor.getComics()

        // assert
        coVerify(exactly = 1) { repository.fetch() }
        confirmVerified(repository)
    }

    @Test
    fun `when execute api getComics return mock success`() = runBlocking {
        // arrange
        val comics = listOf(
            Comic(id = "1", title = "title", description = "description", image = "image"),
            Comic(id = "2", title = "title", description = "description", image = "image"),
            Comic(id = "3", title = "title", description = "description", image = "image"),
        )
        coEvery { repository.fetch() } returns comics

        // act
        interactor.getComics()

        // assert
        coVerify(exactly = 1) { iPresenter.setupList(refEq(comics)) }
        confirmVerified(iPresenter)
    }

    @Test
    fun `when execute api getComics return mock error`() = runBlocking {
        // arrange
        coEvery { repository.fetch() } throws Exception(MOCK_EXCEPTION)

        // act
        interactor.getComics()

        // assert
        coVerify(exactly = 1) { iPresenter.error() }
        confirmVerified(iPresenter)
    }

    private companion object  {
        const val MOCK_EXCEPTION = "Error mockk"
    }
}