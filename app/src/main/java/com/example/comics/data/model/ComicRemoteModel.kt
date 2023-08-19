package com.example.comics.data.model

data class ComicRemoteModel(
    val id: Int,
    val title: String,
    val description: String?,
    val thumbnail: Thumbnail,
) {
    data class Thumbnail(
        val path: String,
        val extension: String,
    )
}