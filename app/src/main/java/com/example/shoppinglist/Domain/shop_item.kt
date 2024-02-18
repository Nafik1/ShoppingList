package com.example.shoppinglist.Domain

data class shop_item (
    val name: String? = null,
    val count: Int? = null,
    val status: Boolean? = null,
    var id: Int = UNDENFINED_ID
)
{
    companion object {
        const val UNDENFINED_ID = -1;
    }
}
