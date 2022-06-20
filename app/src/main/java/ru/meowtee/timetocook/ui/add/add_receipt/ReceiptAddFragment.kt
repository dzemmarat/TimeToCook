package ru.meowtee.timetocook.ui.add.add_receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.FragmentAddReceiptBinding
import ru.meowtee.timetocook.databinding.FragmentReceiptBinding
import ru.meowtee.timetocook.ui.adapter.StepAdapter
import kotlin.properties.Delegates

class ReceiptAddFragment(private val receipt: Receipt) : Fragment() {
    private var binding: FragmentAddReceiptBinding by Delegates.notNull()

    private val stepAdapter by lazy { StepAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddReceiptBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = receipt.title
        binding.tvTime.text = receipt.time
        binding.tvRating.text = getString(R.string.template_rating, receipt.rating.toString())

        binding.rvSteps.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = stepAdapter
        }
        stepAdapter.setItems(receipt.steps)
    }
}