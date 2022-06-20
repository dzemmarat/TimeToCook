package ru.meowtee.timetocook.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.FragmentAddRecipeBinding
import ru.meowtee.timetocook.databinding.FragmentInfoRecipeBinding
import ru.meowtee.timetocook.databinding.FragmentMainMenuBinding
import ru.meowtee.timetocook.ui.adapter.InfoFragmentAdapter
import ru.meowtee.timetocook.ui.add.add_ingridients.IngredientsAddFragment
import ru.meowtee.timetocook.ui.add.add_receipt.ReceiptAddFragment
import ru.meowtee.timetocook.ui.information.ReceiptInfoFragmentArgs
import ru.meowtee.timetocook.ui.information.ingridients.IngredientsFragment
import ru.meowtee.timetocook.ui.information.receipt.ReceiptFragment
import ru.meowtee.timetocook.viewmodels.ReceiptInfoViewModel
import kotlin.properties.Delegates

class AddNewFragment : Fragment() {
    private var binding: FragmentAddRecipeBinding by Delegates.notNull()
    private val viewModel: ReceiptInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddRecipeBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        setupTabLayoutAndPager()
        viewModel.startDatabase(requireContext())
    }

    private fun setupTabLayoutAndPager() {
        with(binding) {
            val adapter = InfoFragmentAdapter(childFragmentManager, lifecycle)

            adapter.addFragment(IngredientsAddFragment(Receipt()), "Ингредиенты")
            adapter.addFragment(ReceiptAddFragment(Receipt()), "Рецепт")

            vpRecipeInfo.adapter = adapter
            TabLayoutMediator(tabLayout, vpRecipeInfo) { tab, position ->
                tab.text = adapter.getPageTitle(position)
            }.attach()
        }
    }
}