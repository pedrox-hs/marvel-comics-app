package com.example.comics.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comics.domain.repository.ComicsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val repository: ComicsRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MainViewState.DEFAULT)
    val state: Flow<MainViewState> by lazy {
        init()
        _state.asStateFlow()
    }

    private fun init() {
        refresh()
    }

    fun refresh() {
        _state.value = MainViewState(isLoading = true)
        viewModelScope.launch {
            val newState = fetchAsState()
            _state.emit(newState)
        }
    }

    private suspend fun fetchAsState(): MainViewState =
        runCatching { repository.fetch() }
            .map { comics ->
                MainViewState(
                    items = comics.map {
                        ItemVO(it)
                    },
                )
            }
            .getOrElse {
                MainViewState(isError = true)
            }
}