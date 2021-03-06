package com.test.thecocktaildb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.test.thecocktaildb.DrinksItem
import com.test.thecocktaildb.R
import com.test.thecocktaildb.databinding.ItemDrinkBinding

class CockTailAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<DrinksItem, CockTailAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemDrinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class PhotoViewHolder(private val binding: ItemDrinkBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(item: DrinksItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.strDrinkThumb)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                textViewUserName.text = item.strDrink
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(drinksItem: DrinksItem)
    }


    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<DrinksItem>() {
            override fun areItemsTheSame(oldItem: DrinksItem, newItem: DrinksItem) =
                oldItem.idDrink == newItem.idDrink

            override fun areContentsTheSame(oldItem: DrinksItem, newItem: DrinksItem) =
                oldItem == newItem
        }
    }
}
