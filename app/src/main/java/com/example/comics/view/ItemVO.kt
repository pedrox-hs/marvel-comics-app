package com.example.comics.view

import com.example.comics.domain.entity.Comic

data class ItemVO(
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