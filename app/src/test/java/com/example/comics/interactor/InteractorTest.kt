package com.example.comics.interactor

import com.example.comics.CoroutinesTestRule
import com.example.comics.presenter.IPresenter
import com.example.comics.repository.DataModel
import com.example.comics.repository.ItemModel
import com.example.comics.repository.Repository
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
    private val repository: Repository = mockk(relaxed = true)

    @Before
    fun setup() {
        interactor = Interactor(iPresenter, repository)
    }

    @Test
    fun `when execute interactor getComics call repository`() = runBlocking {
        // arrange
        coEvery { repository.getComics() } returns ItemModel(data = DataModel(results = listOf()))

        // act
        interactor.getComics()

        // assert
        coVerify(exactly = 1) { repository.getComics() }
        confirmVerified(repository)
    }

    @Test
    fun `when execute api getComics return mock success`() = runBlocking {
        // arrange
        val itemModel = ItemModel(data = DataModel(results = listOf()))
        coEvery { repository.getComics() } returns itemModel

        // act
        interactor.getComics()

        // assert
        coVerify(exactly = 1) { iPresenter.setupList(itemModel) }
        confirmVerified(iPresenter)
    }

    @Test
    fun `when execute api getComics return mock error`() = runBlocking {
        // arrange
        coEvery { repository.getComics() } throws Exception(MOCK_EXCEPTION)

        // act
        interactor.getComics()

        // assert
        coVerify(exactly = 1) { iPresenter.error() }
        confirmVerified(iPresenter)
    }

    private companion object {
        const val MOCK_EXCEPTION = "Error mockk"
    }
}