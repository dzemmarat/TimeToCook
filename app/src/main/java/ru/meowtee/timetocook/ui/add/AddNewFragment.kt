package ru.meowtee.timetocook.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.meowtee.timetocook.databinding.FragmentAddRecipeBinding
import ru.meowtee.timetocook.databinding.FragmentMainMenuBinding
import kotlin.properties.Delegates

class AddNewFragment : Fragment() {
    private var binding: FragmentAddRecipeBinding by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddRecipeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}