package com.example.comics.data.datasource

import com.example.comics.data.model.ComicRemoteModel

interface ComicsDataSource {
    interface Remote : ComicsDataSource {
        suspend fun list(): List<ComicRemoteModel>
    }
}