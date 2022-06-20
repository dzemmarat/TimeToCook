package ru.meowtee.timetocook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.meowtee.timetocook.data.model.Ingredient
import ru.meowtee.timetocook.databinding.ItemIngridientBottomAddBinding
import ru.meowtee.timetocook.databinding.ItemIngridientMiddleAddBinding
import ru.meowtee.timetocook.databinding.ItemIngridientTopAddBinding

class IngredientsAddAdapter : RecyclerView.Adapter<IngredientsAddAdapter.ReceiptsViewHolder>() {
    private var items = emptyList<Ingredient>()
    private var onEditTextAdded: (ingredient: Ingredient, position: Int) -> Unit = { _, _ -> }
    private var onAddBtnClick: () -> Unit = {}

    inner class ReceiptsViewHolder(private val binding: ViewBinding, private val viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Ingredient, position: Int) {
            when (viewType) {
                1 -> {
                    with(binding as ItemIngridientTopAddBinding) {
                        etTitle.doAfterTextChanged {
                            item.name = it.toString()
                            onEditTextAdded(item, position)
                        }
                        etCount.doAfterTextChanged {
                            item.count = it.toString().toDoubleOrNull() ?: 0.0
                            onEditTextAdded(item, position)
                        }
                    }
                }
                3 -> {
                    with(binding as ItemIngridientBottomAddBinding) {
                        root.setOnClickListener {
                            onAddBtnClick()
                        }
                    }
                }
                else -> {
                    with(binding as ItemIngridientMiddleAddBinding) {
                        etTitle.doAfterTextChanged {
                            item.name = it.toString()
                            onEditTextAdded(item, position)
                        }
                        etCount.doAfterTextChanged {
                            item.count = it.toString().toDoubleOrNull() ?: 0.0
                            onEditTextAdded(item, position)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> ReceiptsViewHolder(ItemIngridientTopAddBinding.inflate(inflater), viewType)
            3 -> ReceiptsViewHolder(ItemIngridientBottomAddBinding.inflate(inflater), viewType)
            else -> ReceiptsViewHolder(ItemIngridientMiddleAddBinding.inflate(inflater), viewType)
        }
    }

    override fun onBindViewHolder(holder: ReceiptsViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemViewType(position: Int): Int =
        when {
            position == 0 && items.size > 1 -> 1
            position == items.size - 1 -> 3
            else -> 2
        }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<Ingredient>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun setOnEditTextAddedListener(onEditTextAdded: (ingredient: Ingredient, position: Int) -> Unit) {
        this.onEditTextAdded = onEditTextAdded
    }

    fun setOnAddBtnClickListener(onAddBtnClick: () -> Unit) {
        this.onAddBtnClick = onAddBtnClick
    }
}