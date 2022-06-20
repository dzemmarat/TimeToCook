package ru.meowtee.timetocook.ui.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
import ru.meowtee.timetocook.viewmodels.AddNewViewModel
import ru.meowtee.timetocook.viewmodels.ReceiptInfoViewModel
import kotlin.properties.Delegates

class AddNewFragment : Fragment() {
    private var binding: FragmentAddRecipeBinding by Delegates.notNull()
    private val viewModel: AddNewViewModel by viewModels()

    private val receiptAddFragment = ReceiptAddFragment(Receipt())
    private val ingredientsAddFragment = IngredientsAddFragment(Receipt())

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
        binding.btnClose.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnDone.setOnClickListener {
            Log.e("AAAAAAAAAAAAA", receiptAddFragment.steps.toString())
            viewModel.addReceipt(
                Receipt(
                    steps = receiptAddFragment.stepAdapter.getItems(),
                    title = receiptAddFragment.receipt.title,
                    time = receiptAddFragment.receipt.time,
                    ingredients = ingredientsAddFragment.ingredients,
                    portions = ingredientsAddFragment.portions
                )
            )
            findNavController().navigate(R.id.action_addNewFragment_to_homeFragment)
        }
    }

    private fun setupTabLayoutAndPager() {
        with(binding) {
            val adapter = InfoFragmentAdapter(childFragmentManager, lifecycle)

            adapter.addFragment(ingredientsAddFragment, "Ингредиенты")
            adapter.addFragment(receiptAddFragment, "Рецепт")

            vpRecipeInfo.adapter = adapter
            TabLayoutMediator(tabLayout, vpRecipeInfo) { tab, position ->
                tab.text = adapter.getPageTitle(position)
            }.attach()
        }
    }
}