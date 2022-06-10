package ru.meowtee.timetocook.ui.search.name

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.FragmentSearchByNameBinding
import ru.meowtee.timetocook.ui.adapter.ReceiptsAdapter
import ru.meowtee.timetocook.viewmodels.SearchByNameViewModel
import kotlin.properties.Delegates

class SearchByNameFragment : Fragment() {
    private var binding: FragmentSearchByNameBinding by Delegates.notNull()
    private val viewModel: SearchByNameViewModel by viewModels()

    private val receiptsAdapter by lazy { ReceiptsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchByNameBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        binding.tietQuery.doOnTextChanged { text, _, _, _ ->
            viewModel.findReceipts(text.toString())
            receiptsAdapter.setItems(viewModel.receipts.value)
        }
    }

    private fun setupRecycler() {
        binding.rvReceipts.apply {
            layoutManager = object: LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean = false
            }
            adapter = receiptsAdapter
        }
        viewModel.startDatabase(requireContext())
    }
}