package ru.meowtee.timetocook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import ru.meowtee.timetocook.databinding.ItemStepAddBinding

class StepAddAdapter : RecyclerView.Adapter<StepAddAdapter.StepAddViewHolder>() {
    private var items = mutableListOf<String>()
    private var onAfterTextAdded: (step: String, position: Int) -> Unit = { _, _ -> }
    private var onTextAdded: (step: String, position: Int) -> Unit = { _, _ -> }
    private var onBeforeTextAdded: (step: String, position: Int) -> Unit = { _, _ -> }

    inner class StepAddViewHolder(private val binding: ItemStepAddBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            with(binding) {
                tvStep.text = position.plus(1).toString()
                etStepDescription.doAfterTextChanged {
                    onAfterTextAdded(item, position)
                }
                etStepDescription.doOnTextChanged { text, start, before, count ->
                    onTextAdded(text.toString(), position)
                }
                etStepDescription.doBeforeTextChanged { text, start, count, after ->
                    onBeforeTextAdded(text.toString(), position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepAddViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return StepAddViewHolder(ItemStepAddBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: StepAddViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount() = items.size

    fun setOnEditTextAddedListener(onEditTextAdded: (step: String, position: Int) -> Unit) {
        this.onAfterTextAdded = onEditTextAdded
    }

    fun setOnTextAddedListener(onEditTextAdded: (step: String, position: Int) -> Unit) {
        this.onTextAdded = onEditTextAdded
    }

    fun setOnBeforeTextAddedListener(onEditTextAdded: (step: String, position: Int) -> Unit) {
        this.onBeforeTextAdded = onEditTextAdded
    }

    fun setItems(newItems: MutableList<String>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun setItemsSilent(newItems: MutableList<String>) {
        items = newItems
        notifyItemInserted(newItems.size - 1)
    }

    fun getItems() = items
}