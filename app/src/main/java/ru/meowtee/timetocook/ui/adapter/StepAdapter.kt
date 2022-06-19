package ru.meowtee.timetocook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.meowtee.timetocook.databinding.ItemStepBinding

class StepAdapter : RecyclerView.Adapter<StepAdapter.ReceiptsViewHolder>() {
    private var items = emptyList<String>()

    inner class ReceiptsViewHolder(private val binding: ItemStepBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            with(binding) {
                tvStep.text = position.plus(1).toString()
                tvStepDescription.text = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ReceiptsViewHolder(ItemStepBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: ReceiptsViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }
}