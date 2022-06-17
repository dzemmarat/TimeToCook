package ru.meowtee.timetocook.ui.information.ingridients

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.FragmentIngredientsBinding
import ru.meowtee.timetocook.ui.adapter.IngredientsAdapter
import kotlin.properties.Delegates

class IngredientsFragment(private val receipt: Receipt) : Fragment() {
    private var binding: FragmentIngredientsBinding by Delegates.notNull()
    private val ingredientsAdapter by lazy { IngredientsAdapter() }

    private var count = receipt.copy().portions
    private var ingredients = receipt.ingredients.map { it.copy() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentIngredientsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCount.text = count.toString()

        binding.rvIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = object: LinearLayoutManager(requireContext()) {
                override fun canScrollVertically() = false
            }
        }

        ingredientsAdapter.setItems(ingredients)

        binding.tvPlus.setOnClickListener {
            count++
            binding.tvCount.text = count.toString()
            for (i in 0 until receipt.ingredients.size) {
                ingredients[i].count = receipt.ingredients[i].count / receipt.portions * count
            }
            ingredientsAdapter.setItems(ingredients)
        }

        binding.tvMinus.setOnClickListener {
            count--
            binding.tvCount.text = count.toString()
            for (i in 0 until receipt.ingredients.size) {
                ingredients[i].count = receipt.ingredients[i].count / receipt.portions * count
            }
            ingredientsAdapter.setItems(ingredients)
        }
    }
}