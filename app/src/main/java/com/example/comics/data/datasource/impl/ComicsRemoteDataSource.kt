package com.example.comics.data.datasource.impl

import com.example.comics.data.datasource.ComicsDataSource
import com.example.comics.data.model.ComicRemoteModel
import com.example.comics.data.network.ComicsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

@Factory
class ComicsRemoteDataSource(
    private val comicsApi: ComicsService,
) : ComicsDataSource.Remote {
    override suspend fun list(): List<ComicRemoteModel> =
        withContext(Dispatchers.IO) {
            comicsApi.getComics().data.results
        }
}