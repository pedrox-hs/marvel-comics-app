package com.example.comics.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.comics.databinding.ItemListBinding

class ComicsAdapter : ListAdapter<ComicsVO, ComicsAdapter.ItemViewHolder>(ItemDiffUtil()) {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(item = getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        getItemHolder(parent = parent)

    private fun getItemHolder(parent: ViewGroup) = ItemViewHolder(
        itemBinding = ItemListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    class ItemViewHolder(
        val itemBinding: ItemListBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(
            item: ComicsVO,
        ) = with(itemBinding) {

            Glide.with(itemBinding.root)
                .load(item.image)
                .centerCrop()
                .into(itemBinding.actionImage)

            actionTitle.text = item.title
            actionSubTitle.text = item.subtitle
        }
    }

    internal class ItemDiffUtil : DiffUtil.ItemCallback<ComicsVO>() {
        override fun areItemsTheSame(oldItem: ComicsVO, newItem: ComicsVO): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ComicsVO, newItem: ComicsVO): Boolean =
            oldItem == newItem
    }

}
