package com.example.comics.presentation

sealed interface ComicsViewState {
    val items: List<ComicsVO> get() = emptyList()
    val isError: Boolean get() = false
    val isLoading: Boolean get() = false

    val isListVisible: Boolean
        get() = !isError && !isLoading

    object Initial : ComicsViewState

    object Loading : ComicsViewState {
        override val isLoading = true
    }

    object Error : ComicsViewState {
        override val isError = true
    }

    data class Success(
        override val items: List<ComicsVO>,
    ) : ComicsViewState
}
