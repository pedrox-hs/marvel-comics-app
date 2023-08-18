package com.example.comics.repository

data class ItemModel(
    val data: DataModel
)

data class DataModel(
    val results: List<ResultModel>
)

data class ResultModel(
    val id: Int,
    val title: String,
    val description: String?,
    val thumbnail: ThumbnailModel
)

data class ThumbnailModel(
    val path: String,
    val extension: String,
)