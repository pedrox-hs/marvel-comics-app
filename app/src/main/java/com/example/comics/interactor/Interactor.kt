package com.example.comics.interactor

import com.example.comics.domain.repository.ComicsRepository
import com.example.comics.presenter.IPresenter
import com.example.comics.util.Result.Failure
import com.example.comics.util.Result.Success
import com.example.comics.util.safeRunDispatcher
import org.koin.core.annotation.Factory

@Factory
class Interactor(
    private val iPresenter: IPresenter,
    private val repository: ComicsRepository,
) : IInteractor {


    override suspend fun getComics() {
        when (val result = safeRunDispatcher {
            repository.fetch()
        }) {
            is Success -> iPresenter.setupList(result.data)
            is Failure -> iPresenter.error()
        }
    }

}