package ru.meowtee.timetocook.ui.search.name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.FragmentSearchByNameBinding
import ru.meowtee.timetocook.ui.rand_receipt.adapter.ReceiptsAdapter
import kotlin.properties.Delegates

class SearchByNameFragment : Fragment() {
    private var binding: FragmentSearchByNameBinding by Delegates.notNull()

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
    }

    private fun setupRecycler() {
        binding.rvReceipts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = receiptsAdapter
        }
        receiptsAdapter.setItems(
            listOf(
                Receipt(
                    image = R.drawable.ic_dish_2,
                    name = "Французскиe круасаны с шоколадным соусом",
                    isFavourite = false,
                    tags = emptyList()
                ),
                Receipt(
                    image = R.drawable.ic_dish,
                    name = "Пряный тыквенный суп",
                    isFavourite = false,
                    tags = emptyList()
                ),
            )
        )
    }
}