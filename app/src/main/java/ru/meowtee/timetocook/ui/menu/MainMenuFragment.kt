package ru.meowtee.timetocook.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.databinding.FragmentMainMenuBinding
import kotlin.properties.Delegates

class MainMenuFragment : Fragment() {
    private var binding: FragmentMainMenuBinding by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnSearchName.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchByNameFragment)
            }
            btnSearchTags.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchByTagFragment)
            }
            btnAllRecipes.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_allReceiptsFragment)
            }
            btnFavourite.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_favouriteFragment)
            }
            btnRandom.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_randomFragment)
            }
            btnRecommendations.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_recommendationsFragment)
            }
        }
    }
}