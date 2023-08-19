package com.example.comics.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comics.domain.repository.ComicsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ComicsViewModel(
    private val repository: ComicsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ComicsViewState.DEFAULT)
    val state: Flow<ComicsViewState> by lazy {
        init()
        _state.asStateFlow()
    }

    private fun init() {
        refresh()
    }

    fun refresh() {
        _state.value = ComicsViewState(isLoading = true)
        viewModelScope.launch {
            val newState = fetchAsState()
            _state.emit(newState)
        }
    }

    private suspend fun fetchAsState(): ComicsViewState =
        runCatching { repository.fetch() }
            .map { comics ->
                ComicsViewState(
                    items = comics.map {
                        ComicsVO(it)
                    },
                )
            }
            .getOrElse {
                ComicsViewState(isError = true)
            }
}