package ru.meowtee.timetocook.ui.information

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
import ru.meowtee.timetocook.databinding.FragmentInfoRecipeBinding
import ru.meowtee.timetocook.ui.adapter.InfoFragmentAdapter
import ru.meowtee.timetocook.ui.information.ingridients.IngredientsFragment
import ru.meowtee.timetocook.ui.information.receipt.ReceiptFragment
import ru.meowtee.timetocook.viewmodels.ReceiptInfoViewModel
import kotlin.properties.Delegates

class ReceiptInfoFragment : Fragment() {
    private var binding: FragmentInfoRecipeBinding by Delegates.notNull()
    private val args: ReceiptInfoFragmentArgs by navArgs()
    private val viewModel: ReceiptInfoViewModel by viewModels()

    private var isFavourite = false
    private var isTimerEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentInfoRecipeBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.tvClock.stop()
        Glide.with(requireContext())
            .load(args.receipt.image)
            .into(binding.ivDish)

        setupTabLayoutAndPager()
        viewModel.startDatabase(requireContext())

        isFavourite = args.receipt.isFavourite
        binding.btnLike.apply {
            changeDrawableLike()
            setOnClickListener {
                viewModel.changeReceipt(args.receipt)
                changeDrawableLike()
            }
        }
        binding.tvClock.setOnClickListener {
            if (isTimerEnabled) binding.tvClock.start()
            else binding.tvClock.stop()
            isTimerEnabled = !isTimerEnabled
        }

        binding.btnEdit.setOnClickListener {
            findNavController().navigate(ReceiptInfoFragmentDirections.actionReceiptInfoFragmentToAddNewFragment(
                receipt = args.receipt,
                isEditable = true
            ))
        }
    }

    private fun changeDrawableLike() {
        Glide.with(requireContext())
            .load(
                if (isFavourite) R.drawable.ic_heart
                else R.drawable.ic_heart_outline
            )
            .into(binding.btnLike)
        isFavourite = !isFavourite
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