package com.example.comics.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.comics.databinding.FragmentComicsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComicsFragment : Fragment() {

    private val viewModel: ComicsViewModel by viewModel()

    private var _binding: FragmentComicsBinding? = null
    private val binding get() = _binding!!

    private val adapter = ComicsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentComicsBinding.inflate(inflater, container, false).bind()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect(::handleState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentComicsBinding.bind() = apply {
        listItem.adapter = adapter
        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun handleState(state: ComicsViewState) = binding.run {
        swipeRefresh.isRefreshing = state.isLoading
        errorTV.isVisible = state.isError
        listItem.isVisible = state.isListVisible
        adapter.submitList(state.items)
    }
}