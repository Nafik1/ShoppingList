package com.example.shoppinglist.Domain.entity

data class shop_item (
    val name: String,
    val count: Int,
    val status: Boolean,
    var id: Int = UNDENFINED_ID
)
{
    companion object {
        const val UNDENFINED_ID = 0;
    }
}
