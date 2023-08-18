package com.example.comics.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.comics.databinding.ItemListBinding

class Adapter : ListAdapter<ItemVO, Adapter.ItemViewHolder>(ItemDiffUtil()) {

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
            item: ItemVO,
        ) = with(itemBinding) {

            Glide.with(itemBinding.root)
                .load(item.image)
                .centerCrop()
                .into(itemBinding.actionImage)

            actionTitle.text = item.title
            actionSubTitle.text = item.subtitle
        }
    }

    internal class ItemDiffUtil : DiffUtil.ItemCallback<ItemVO>() {
        override fun areItemsTheSame(oldItem: ItemVO, newItem: ItemVO): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItemVO, newItem: ItemVO): Boolean =
            oldItem == newItem
    }

}
