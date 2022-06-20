package ru.meowtee.timetocook.ui.adapter

import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import ru.meowtee.timetocook.data.model.Ingredient
import ru.meowtee.timetocook.databinding.ItemStepAddBinding
import ru.meowtee.timetocook.databinding.ItemStepBinding
import java.util.logging.Handler

class StepAddAdapter : RecyclerView.Adapter<StepAddAdapter.StepAddViewHolder>() {
    private var items = emptyList<String>()
    private var onEditTextAdded: (step: String, position: Int) -> Unit = { _, _ -> }

    inner class StepAddViewHolder(private val binding: ItemStepAddBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            with(binding) {
                tvStep.text = position.plus(1).toString()
                etStepDescription.doAfterTextChanged {
                    onEditTextAdded(item, position)
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
        this.onEditTextAdded = onEditTextAdded
    }

    fun setItems(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }
}