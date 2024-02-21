package com.example.shoppinglist.Domain

data class shop_item (
    val name: String,
    val count: Int,
    val status: Boolean,
    var id: Int = UNDENFINED_ID
)
{
    companion object {
        const val UNDENFINED_ID = -1;
    }
}
