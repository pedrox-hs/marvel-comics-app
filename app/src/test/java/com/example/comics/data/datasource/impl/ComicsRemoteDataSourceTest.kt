package com.example.comics.data.datasource.impl

import com.example.comics.CoroutinesTestRule
import com.example.comics.data.model.ComicRemoteModel
import com.example.comics.data.model.ResponseModel
import com.example.comics.data.network.ComicsService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ComicsRemoteDataSourceTest {
    private val comicsApi: ComicsService = mockk()
    private lateinit var dataSource: ComicsRemoteDataSource

    @get:Rule
    val rule = CoroutinesTestRule()

    @Before
    fun setup() {
        dataSource = ComicsRemoteDataSource(comicsApi)
    }

    @Test
    fun `when execute list call comicsApi`() = runTest {
        // arrange
        coEvery { comicsApi.getComics() } returns ResponseModel(data = ResponseModel.Data(results = listOf()))

        // act
        dataSource.list()

        // assert
        coVerify { comicsApi.getComics() }
    }

    @Test
    fun `when execute list return mock success`() = runTest {
        // arrange
        val expected = comicRemoteModels()
        coEvery { comicsApi.getComics() } returns ResponseModel(data = ResponseModel.Data(results = expected))

        // act
        val actual = dataSource.list()

        // assert
        assert(actual == expected) {
            "`actual` should be equals `expected`" +
                    "\nactual: $actual" +
                    "\nexpected: $expected"
        }
    }
}

private fun comicRemoteModels() = listOf(
    ComicRemoteModel(
        id = 1,
        title = "title",
        description = "description",
        thumbnail = ComicRemoteModel.Thumbnail("path1", "ext1"),
    ),
    ComicRemoteModel(
        id = 2,
        title = "title",
        description = "description",
        thumbnail = ComicRemoteModel.Thumbnail("path2", "ext2"),
    ),
    ComicRemoteModel(
        id = 3,
        title = "title",
        description = "description",
        thumbnail = ComicRemoteModel.Thumbnail("path3", "ext3"),
    ),
)