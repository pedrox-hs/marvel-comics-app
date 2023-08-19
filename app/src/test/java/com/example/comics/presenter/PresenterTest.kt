package com.example.comics.presenter

import com.example.comics.domain.entity.Comic
import com.example.comics.view.IView
import com.example.comics.view.ItemVO
import io.mockk.MockKAnnotations
import io.mockk.called
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
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
        val comic = Comic(id = "1", title = "title", description = "description", image = "image")
        val expectedItems = listOf(ItemVO(
            id = comic.id,
            title = comic.title,
            subtitle = comic.description,
            image = comic.image,
        ))

        // act
        presenter.setupList(listOf(comic))

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