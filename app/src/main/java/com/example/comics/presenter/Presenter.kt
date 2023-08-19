package com.example.comics.presenter

import com.example.comics.domain.entity.Comic
import com.example.comics.view.IView
import com.example.comics.view.ItemVO
import org.koin.core.annotation.Factory

@Factory
class Presenter(
    private val iview: IView,
) : IPresenter {

    override fun setupList(list: List<Comic>) {
        iview.viewList(list = list.map { comic ->
            ItemVO(
                id = comic.id,
                title = comic.title,
                subtitle = comic.description,
                image = comic.image,
            )
        })
    }

    override fun error() {
        iview.error()
    }
}