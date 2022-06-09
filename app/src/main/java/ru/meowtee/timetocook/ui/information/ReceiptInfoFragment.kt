package ru.meowtee.timetocook.ui.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.meowtee.timetocook.databinding.FragmentInfoRecipeBinding
import ru.meowtee.timetocook.databinding.FragmentMainMenuBinding
import kotlin.properties.Delegates

class ReceiptInfoFragment : Fragment() {
    private var binding: FragmentInfoRecipeBinding by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentInfoRecipeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}