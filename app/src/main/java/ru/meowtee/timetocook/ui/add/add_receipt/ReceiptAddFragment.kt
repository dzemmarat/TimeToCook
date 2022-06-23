package ru.meowtee.timetocook.ui.add.add_receipt

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.FragmentAddReceiptBinding
import ru.meowtee.timetocook.databinding.FragmentReceiptBinding
import ru.meowtee.timetocook.ui.adapter.StepAdapter
import ru.meowtee.timetocook.ui.adapter.StepAddAdapter
import kotlin.properties.Delegates

class ReceiptAddFragment(val receipt: Receipt) : Fragment() {
    private var binding: FragmentAddReceiptBinding by Delegates.notNull()
    val stepAdapter by lazy { StepAddAdapter() }

    var steps = receipt.steps
    var rating = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddReceiptBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etTitle.setText(receipt.title)
        binding.etTime.setText(receipt.time)
        binding.etRating.setText(receipt.rating.toString())

        binding.etRating.doOnTextChanged { text, start, before, count ->
            rating = text.toString().toIntOrNull() ?: 0
        }

        binding.rvSteps.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = stepAdapter
        }
        steps += ""
        stepAdapter.setItems(steps)
        stepAdapter.setOnTextAddedListener { item, position ->
            Log.e("AAAAAAAAAAAAA", "WORKRKRKR")
            steps[position] = item
            Log.e("AAAAAAAAAAAAA", steps.toString())
        }
        stepAdapter.setOnBeforeTextAddedListener { step, position ->
            Handler(Looper.getMainLooper()).postDelayed({
                if (position == steps.size - 1) {
                    steps.add("")
                    stepAdapter.setItemsSilent(steps)
                }
            },1000)
        }
        binding.etTitle.doAfterTextChanged {
            receipt.title = it.toString()
        }
        binding.etTime.doAfterTextChanged {
            receipt.time = it.toString()
        }
    }
}