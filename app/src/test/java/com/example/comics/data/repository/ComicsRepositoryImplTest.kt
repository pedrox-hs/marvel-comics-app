package com.example.comics.data.repository

import com.example.comics.data.datasource.ComicsDataSource
import com.example.comics.data.model.ComicRemoteModel
import com.example.comics.domain.entity.Comic
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ComicsRepositoryImplTest {
    private val remoteDataSource: ComicsDataSource.Remote = mockk(relaxed = true)
    private lateinit var repository: ComicsRepositoryImpl

    @Before
    fun setup() {
        repository = ComicsRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `when execute fetch call remoteDataSource`() = runTest {
        // arrange
        coEvery { remoteDataSource.list() } returns listOf()

        // act
        repository.fetch()

        // assert
        coEvery { remoteDataSource.list() }
    }

    @Test
    fun `when execute fetch return mock success`() = runTest {
        // arrange
        val expected = comicList()
        coEvery { remoteDataSource.list() } returns comicRemoteModels()

        // act
        val actual = repository.fetch()

        // assert
        assertThat(actual, equalTo(expected))
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

private fun comicList() = listOf(
    Comic(
        id = "1",
        title = "title",
        description = "description",
        image = "path1.ext1",
    ),
    Comic(
        id = "2",
        title = "title",
        description = "description",
        image = "path2.ext2",
    ),
    Comic(
        id = "3",
        title = "title",
        description = "description",
        image = "path3.ext3",
    ),
)