package ru.meowtee.timetocook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.meowtee.timetocook.data.model.Recommendation
import ru.meowtee.timetocook.databinding.ItemRecommendationBinding

class RecommendationsAdapter :
    RecyclerView.Adapter<RecommendationsAdapter.RecommendationsViewHolder>() {
    private var items = emptyList<Recommendation>()

    inner class RecommendationsViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recommendation, position: Int) {
            with(binding) {
                tvTitle.text = "${position.plus(1)} ${item.title}"
                tvDescription.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RecommendationsViewHolder(ItemRecommendationBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<Recommendation>) {
        items = newItems
        notifyDataSetChanged()
    }
}