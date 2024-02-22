package com.example.shoppinglist.Presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglist.Domain.shop_item

class shopListDiffCallback(
    private val oldlist: List<shop_item>,
    private val newlist: List<shop_item>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldlist.size
    }

    override fun getNewListSize(): Int {
        return newlist.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val olditem = oldlist[oldItemPosition]
        val newitem = newlist[newItemPosition]
        return olditem.id == newitem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val olditem = oldlist[oldItemPosition]
        val newitem = newlist[newItemPosition]
        return olditem == newitem
    }
}