package com.example.comics.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.coroutineScope
import com.example.comics.databinding.ActivityMainBinding
import com.example.comics.interactor.Interactor
import com.example.comics.presenter.Presenter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), IView {

    private val interactor: Interactor = Interactor(Presenter(this))

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