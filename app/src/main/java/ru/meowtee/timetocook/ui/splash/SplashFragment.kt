package ru.meowtee.timetocook.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import ru.meowtee.timetocook.databinding.FragmentSplashBinding
import ru.meowtee.timetocook.viewmodels.RandomReceiptViewModel
import ru.meowtee.timetocook.viewmodels.SplashViewModel
import kotlin.properties.Delegates

class SplashFragment : Fragment() {
    private var binding: FragmentSplashBinding by Delegates.notNull()
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashBinding.inflate(inflater)
        exitTransition = MaterialElevationScale(false).apply {
            duration = 400L
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 400L
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        val emailCardDetailTransitionName = "textLogo"
        val extras = FragmentNavigatorExtras(binding.tvLogo to emailCardDetailTransitionName)
        val directions = SplashFragmentDirections.actionMainFragmentToRandomReceiptFragment()
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(directions, extras)
        }, 1000)
    }

}