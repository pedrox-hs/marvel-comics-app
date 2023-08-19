package com.example.comics.data.model

class ResponseModel<T>(
    val data: Data<T>,
) {
    class Data<T>(
        val results: List<T>
    )
}
