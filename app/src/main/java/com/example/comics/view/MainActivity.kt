package com.example.comics.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.comics.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private var binding: ActivityMainBinding? = null
    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.state.collect(::handleState)
        }
    }

    private fun bindView() {
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            listItem.adapter = adapter
            swipeRefresh.setOnRefreshListener {
                viewModel.refresh()
            }
        }
    }

    private fun handleState(state: MainViewState) = binding?.run {
        swipeRefresh.isRefreshing = state.isLoading
        errorTV.isVisible = state.isError
        listItem.isVisible = state.isListVisible
        adapter.submitList(state.items)
    }
}