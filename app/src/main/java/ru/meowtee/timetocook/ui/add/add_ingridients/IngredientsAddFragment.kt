package ru.meowtee.timetocook.ui.add.add_ingridients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.meowtee.timetocook.data.model.Ingredient
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.FragmentAddIngredientsBinding
import ru.meowtee.timetocook.ui.adapter.IngredientsAddAdapter
import kotlin.properties.Delegates

class IngredientsAddFragment(private val receipt: Receipt) : Fragment() {
    private var binding: FragmentAddIngredientsBinding by Delegates.notNull()
    private val ingredientsAdapter by lazy { IngredientsAddAdapter() }

    private var count = receipt.copy().portions
    private var ingredients = receipt.ingredients.map { it.copy() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddIngredientsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCount.text = count.toString()

        binding.rvIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = object : LinearLayoutManager(requireContext()) {
                override fun canScrollVertically() = false
            }
        }

        ingredients = ingredients + listOf(Ingredient("", 0.0))
        ingredientsAdapter.setItems(ingredients)

        ingredientsAdapter.setOnAddBtnClickListener {
            ingredients = ingredients + listOf(Ingredient("", 0.0))
            ingredientsAdapter.setItems(ingredients)
        }

        binding.tvPlus.setOnClickListener {
            count++
            binding.tvCount.text = count.toString()
        }

        binding.tvMinus.setOnClickListener {
            count--
            binding.tvCount.text = count.toString()
        }
    }
}