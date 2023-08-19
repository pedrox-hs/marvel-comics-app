package com.example.comics.data.repository

import com.example.comics.data.datasource.ComicsDataSource
import com.example.comics.domain.entity.Comic
import com.example.comics.domain.repository.ComicsRepository
import org.koin.core.annotation.Factory

@Factory
class ComicsRepositoryImpl(
    private val remoteDataSource: ComicsDataSource.Remote,
) : ComicsRepository {
    override suspend fun fetch(): List<Comic> =
        remoteDataSource.list()
            .map { model ->
                Comic(
                    id = "${model.id}",
                    title = model.title,
                    description = model.description ?: "",
                    image = "${model.thumbnail.path}.${model.thumbnail.extension}",
                )
            }
}