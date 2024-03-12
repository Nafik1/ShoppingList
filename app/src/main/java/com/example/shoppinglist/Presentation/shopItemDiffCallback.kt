package com.example.shoppinglist.Presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglist.Domain.entity.shop_item

class shopItemDiffCallback(

) : DiffUtil.ItemCallback<shop_item>() {
    override fun areItemsTheSame(oldItem: shop_item, newItem: shop_item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: shop_item, newItem: shop_item): Boolean {
        return oldItem == newItem
    }
}