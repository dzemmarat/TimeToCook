package ru.meowtee.timetocook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import ru.meowtee.timetocook.data.model.Ingredient
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.ItemIngridientBottomBinding
import ru.meowtee.timetocook.databinding.ItemIngridientMiddleBinding
import ru.meowtee.timetocook.databinding.ItemIngridientTopBinding

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.ReceiptsViewHolder>() {
    private var items = emptyList<Ingredient>()

    inner class ReceiptsViewHolder(private val binding: ViewBinding, private val viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Ingredient) {
            when (viewType) {
                1 -> {
                    with(binding as ItemIngridientTopBinding) {
                        tvTitle.text = item.name
                        tvCount.text = item.count.toString()
                    }
                }
                3 -> {
                    with(binding as ItemIngridientBottomBinding) {
                        tvTitle.text = item.name
                        tvCount.text = item.count.toString()
                    }
                }
                else -> {
                    with(binding as ItemIngridientMiddleBinding) {
                        tvTitle.text = item.name
                        tvCount.text = item.count.toString()
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            1 -> ReceiptsViewHolder(ItemIngridientTopBinding.inflate(inflater), viewType)
            3 -> ReceiptsViewHolder(ItemIngridientBottomBinding.inflate(inflater), viewType)
            else -> ReceiptsViewHolder(ItemIngridientMiddleBinding.inflate(inflater), viewType)
        }
    }

    override fun onBindViewHolder(holder: ReceiptsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> 1
            items.size - 1 -> 3
            else -> 2
        }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<Ingredient>) {
        items = newItems
        notifyDataSetChanged()
    }
}