package ru.meowtee.timetocook.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.ItemReceiptBinding

class ReceiptsAdapter : RecyclerView.Adapter<ReceiptsAdapter.ReceiptsViewHolder>() {
    private var items = emptyList<Receipt>()
    private var onItemClickListener: () -> Unit = {}

    inner class ReceiptsViewHolder(private val binding: ItemReceiptBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Receipt) {
            with(binding) {
                tvName.text = item.name

                Glide.with(root)
                    .load(item.image)
                    .into(ivDish)

                btnCheck.setOnClickListener {
                    onItemClickListener()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceiptsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ReceiptsViewHolder(ItemReceiptBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: ReceiptsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<Receipt>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onItemClickListener: () -> Unit) {
        this.onItemClickListener = onItemClickListener
    }
}