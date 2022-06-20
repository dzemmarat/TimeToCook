package ru.meowtee.timetocook.ui.search.tags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.meowtee.timetocook.databinding.FragmentSearchByTagBinding
import ru.meowtee.timetocook.ui.adapter.ReceiptsAdapter
import ru.meowtee.timetocook.viewmodels.SearchByTagViewModel
import kotlin.properties.Delegates

class SearchByTagFragment : Fragment() {
    private var binding: FragmentSearchByTagBinding by Delegates.notNull()
    private val viewModel: SearchByTagViewModel by viewModels()

    private val receiptsAdapter by lazy { ReceiptsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchByTagBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupChipDifficult()
    }

    private fun setupChipDifficult() {
        binding.easy.setOnClickListener {
            viewModel.findReceipts("Простой")
        }
        binding.medium.setOnClickListener {
            viewModel.findReceipts("Средний")
        }
        binding.hard.setOnClickListener {
            Log.e("AAAAAAAAAAAAA", viewModel.receipts.value.toString())
            viewModel.findReceipts("Сложный")
        }
    }

    private fun setupRecycler() {
        binding.rvReceipts.apply {
            layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean = false
            }
            adapter = receiptsAdapter
        }
        viewModel.startDatabase(requireContext())
        lifecycleScope.launch {
            viewModel.receipts.collect {
                Log.e("AAAAAAAAAAAAA", it.toString())
                receiptsAdapter.setItems(it)
            }
        }
    }
}