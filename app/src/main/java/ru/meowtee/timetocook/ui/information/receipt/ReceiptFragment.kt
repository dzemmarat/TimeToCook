package ru.meowtee.timetocook.ui.information.receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.FragmentReceiptBinding
import kotlin.properties.Delegates

class ReceiptFragment(private val receipt: Receipt) : Fragment() {
    private var binding: FragmentReceiptBinding by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReceiptBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.text = receipt.title
    }
}