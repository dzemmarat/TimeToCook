package ru.meowtee.timetocook.ui.add.add_receipt

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.FragmentAddReceiptBinding
import ru.meowtee.timetocook.databinding.FragmentReceiptBinding
import ru.meowtee.timetocook.ui.adapter.StepAdapter
import ru.meowtee.timetocook.ui.adapter.StepAddAdapter
import kotlin.properties.Delegates

class ReceiptAddFragment(private val receipt: Receipt) : Fragment() {
    private var binding: FragmentAddReceiptBinding by Delegates.notNull()
    private val stepAdapter by lazy { StepAddAdapter() }
    var title = ""
    var time = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddReceiptBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSteps.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = stepAdapter
        }
        receipt.steps += ""
        stepAdapter.setItems(receipt.steps)
        stepAdapter.setOnEditTextAddedListener { item, position ->
            receipt.steps[position] = item
            Handler(Looper.getMainLooper()).postDelayed({
                if (position == receipt.steps.size - 1) {
                    receipt.steps.add("")
                    stepAdapter.setItems(receipt.steps)
                }
            },1000)
        }
        binding.etTitle.doAfterTextChanged {
            title = it.toString()
        }
        binding.etTime.doAfterTextChanged {
            time = it.toString()
        }
    }
}