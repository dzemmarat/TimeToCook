package ru.meowtee.timetocook.ui.recommendations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import ru.meowtee.timetocook.databinding.FragmentRecommendationBinding
import ru.meowtee.timetocook.ui.adapter.RecommendationsAdapter
import ru.meowtee.timetocook.viewmodels.RecommendationViewModel
import kotlin.properties.Delegates

class RecommendationsFragment : Fragment() {
    private var binding: FragmentRecommendationBinding by Delegates.notNull()
    private val viewModel: RecommendationViewModel by viewModels()

    private val recommendationsAdapter by lazy { RecommendationsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRecommendationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        viewModel.findRecommendations()
    }

    private fun setupRecycler() {
        binding.rvRecommendations.apply {
            layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically(): Boolean = false
            }
            adapter = recommendationsAdapter
        }
        viewModel.startDatabase(requireContext())
        lifecycleScope.launchWhenCreated {
            viewModel.recommendations.collect {
                recommendationsAdapter.setItems(viewModel.recommendations.value)
            }
        }
    }
}