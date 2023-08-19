package com.example.comics.view

data class MainViewState(
    val items: List<ItemVO> = emptyList(),
    val isError: Boolean = false,
    val isLoading: Boolean = false,
) {
    val isListVisible: Boolean
        get() = !isError && !isLoading

    companion object {
        val DEFAULT = MainViewState()
    }
}
