package ru.meowtee.timetocook.ui.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import ru.meowtee.timetocook.databinding.FragmentInfoRecipeBinding
import ru.meowtee.timetocook.databinding.FragmentMainMenuBinding
import ru.meowtee.timetocook.ui.adapter.InfoFragmentAdapter
import ru.meowtee.timetocook.ui.information.ingridients.IngredientsFragment
import ru.meowtee.timetocook.ui.information.receipt.ReceiptFragment
import kotlin.properties.Delegates

class ReceiptInfoFragment : Fragment() {
    private var binding: FragmentInfoRecipeBinding by Delegates.notNull()
    private val args: ReceiptInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentInfoRecipeBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Glide.with(requireContext())
            .load(args.receipt.image)
            .into(binding.ivDish)
        setupTabLayoutAndPager()
    }

    private fun setupTabLayoutAndPager() {
        with(binding) {
            val adapter = InfoFragmentAdapter(childFragmentManager, lifecycle)

            adapter.addFragment(IngredientsFragment(args.receipt), "Ингредиенты")
            adapter.addFragment(ReceiptFragment(args.receipt), "Рецепт")

            vpRecipeInfo.adapter = adapter
            TabLayoutMediator(tabLayout, vpRecipeInfo) { tab, position ->
                tab.text = adapter.getPageTitle(position)
            }.attach()
        }
    }
}