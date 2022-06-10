package ru.meowtee.timetocook.ui.search.tags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.ChipGroup
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.FragmentMainMenuBinding
import ru.meowtee.timetocook.databinding.FragmentSearchByTagBinding
import ru.meowtee.timetocook.ui.adapter.ReceiptsAdapter
import ru.meowtee.timetocook.viewmodels.SearchByNameViewModel
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
        binding.chipGroupDifficult.setOnCheckedStateChangeListener { group, checkedIds ->
            when(checkedIds[0]) {
                R.id.easy -> {

                }
            }
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