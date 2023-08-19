package com.example.comics.presenter

import com.example.comics.repository.DataModel
import com.example.comics.repository.ItemModel
import com.example.comics.repository.ResultModel
import com.example.comics.repository.ThumbnailModel
import com.example.comics.view.IView
import com.example.comics.view.ItemVO
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class PresenterTest {

    private lateinit var presenter: Presenter

    private val iView: IView = mockk(relaxed = true)

    @Before
    fun setup() {
        presenter = Presenter(iView)
    }

    @Test
    fun `when execute setupList`() {
        // arrange
        val comic = ResultModel(
            title = "title",
            description = "description",
            thumbnail = ThumbnailModel(path = "path", extension = "extension"),
        )
        val expectedItems = listOf(
            ItemVO(
                title = comic.title,
                subtitle = comic.description!!,
                image = "${comic.thumbnail.path}.${comic.thumbnail.extension}",
            )
        )

        // act
        presenter.setupList(ItemModel(data = DataModel(results = listOf(comic))))

        // assert
        verify(exactly = 1) { iView.viewList(expectedItems) }
        confirmVerified(iView)
    }

    @Test
    fun `when execute error`() {
        // act
        presenter.error()

        // assert
        verify(exactly = 1) { iView.error() }
        confirmVerified(iView)
    }
}