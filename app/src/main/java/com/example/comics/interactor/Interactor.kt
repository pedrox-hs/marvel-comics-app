package com.example.comics.interactor

import com.example.comics.domain.repository.ComicsRepository
import com.example.comics.presenter.IPresenter
import org.koin.core.annotation.Factory

@Factory
class Interactor(
    private val iPresenter: IPresenter,
    private val repository: ComicsRepository,
) : IInteractor {
    override suspend fun getComics() {
        runCatching { repository.fetch() }
            .onSuccess {
                iPresenter.setupList(it)
            }
            .onFailure {
                iPresenter.error()
            }
    }
}