package ru.meowtee.timetocook.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import ru.meowtee.timetocook.databinding.FragmentAllReceiptsBinding
import ru.meowtee.timetocook.databinding.FragmentFavouritesBinding
import ru.meowtee.timetocook.ui.adapter.ReceiptsAdapter
import ru.meowtee.timetocook.viewmodels.AllReceiptsViewModel
import ru.meowtee.timetocook.viewmodels.FavouriteViewModel
import kotlin.properties.Delegates

class FavouriteFragment : Fragment() {
    private var binding: FragmentFavouritesBinding by Delegates.notNull()
    private val viewModel: FavouriteViewModel by viewModels()

    private val receiptsAdapter by lazy { ReceiptsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        viewModel.findReceipts()
    }

    private fun setupRecycler() {
        binding.rvReceipts.apply {
            layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean = false
            }
            adapter = receiptsAdapter
        }
        receiptsAdapter.setOnItemClickListener { receipt ->
            findNavController().navigate(FavouriteFragmentDirections.actionFavouriteFragmentToReceiptInfoFragment(
                receipt = receipt
            ))
        }
        receiptsAdapter.setOnHeartClickListener { receipt ->
            viewModel.changeReceipt(receipt)
        }
        viewModel.startDatabase(requireContext())
        lifecycleScope.launchWhenCreated {
            viewModel.receipts.collect {
                receiptsAdapter.setItems(viewModel.receipts.value)
            }
        }
    }
}