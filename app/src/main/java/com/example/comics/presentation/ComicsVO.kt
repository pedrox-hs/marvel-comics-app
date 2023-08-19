package com.example.comics.presentation

import com.example.comics.domain.entity.Comic

data class ComicsVO(
    val id: String,
    var image: String,
    var title: String,
    var subtitle: String
) {
    constructor(comic: Comic) : this(
        id = comic.id,
        image = comic.image,
        title = comic.title,
        subtitle = comic.description
    )
}