package com.example.comics.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.coroutineScope
import com.example.comics.databinding.ActivityMainBinding
import com.example.comics.interactor.IInteractor
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), IView {

    private val interactor: IInteractor by inject {
        parametersOf(this)
    }

    private var binding: ActivityMainBinding? = null
    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindView()
        refresh()
        swipeList()
    }

    private fun bindView() {
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            listItem.adapter = adapter
        }
    }

    private fun swipeList() {
        binding?.swipeRefresh?.setOnRefreshListener {
            refresh()
        }
    }

    override fun refresh() {
        binding?.swipeRefresh?.isRefreshing = true
        lifecycle.coroutineScope.launch {
            interactor.getComics()
        }
    }

    override fun viewList(list: List<ItemVO>) {
        adapter.submitList(list)
        binding?.run {
            errorTV.isGone = true
            listItem.isVisible = true
            swipeRefresh.isRefreshing = false
        }
    }

    override fun error() {
        binding?.run {
            listItem.isGone = true
            errorTV.isVisible = true
            swipeRefresh.isRefreshing = false
        }
    }
}