package ru.meowtee.timetocook.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.meowtee.timetocook.R
import ru.meowtee.timetocook.data.model.Receipt
import ru.meowtee.timetocook.databinding.ItemReceiptBinding

class ReceiptsAdapter : RecyclerView.Adapter<ReceiptsAdapter.ReceiptsViewHolder>() {
    private var items = emptyList<Receipt>()
    private var onItemClickListener: (title: Receipt) -> Unit = {}
    private var onHeartItemClickListener: (receipt: Receipt) -> Unit = {}

    inner class ReceiptsViewHolder(private val binding: ItemReceiptBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isFavourite = false

        fun bind(item: Receipt) {
            with(binding) {
                tvName.text = item.title

                Glide.with(root)
                    .load(item.image)
                    .into(ivDish)

                btnCheck.setOnClickListener {
                    onItemClickListener(item)
                }

                isFavourite = item.isFavourite
                binding.ivFavourite.apply {
                    changeDrawableLike(binding.root.context)
                    setOnClickListener {
                        onHeartItemClickListener(item)
                        changeDrawableLike(binding.root.context)
                    }
                }
            }
        }

        private fun changeDrawableLike(context: Context) {
            Glide.with(context)
                .load(
                    if (isFavourite) R.drawable.ic_heart
                    else R.drawable.ic_heart_outline
                )
                .into(binding.ivFavourite)
            isFavourite = !isFavourite
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

    fun setOnItemClickListener(onItemClickListener: (receipt: Receipt) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnHeartClickListener(onHeartClickListener: (receipt: Receipt) -> Unit) {
        onHeartItemClickListener = onHeartClickListener
    }
}