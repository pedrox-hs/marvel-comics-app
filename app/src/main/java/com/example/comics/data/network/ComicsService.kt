package com.example.comics.data.network

import com.example.comics.data.model.ComicRemoteModel
import com.example.comics.data.model.ResponseModel
import retrofit2.http.GET

interface ComicsService {
    @GET("comics")
    suspend fun getComics(): ResponseModel<ComicRemoteModel>
}