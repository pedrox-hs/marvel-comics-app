package com.example.comics.presentation

data class ComicsViewState(
    val items: List<ComicsVO> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
) {
    val isListVisible: Boolean
        get() = !isError && !isLoading

    companion object {
        val DEFAULT = ComicsViewState()
    }
}
