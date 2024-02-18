package com.example.shoppinglist.Domain

import androidx.lifecycle.LiveData

interface shop_list_repository {
    fun addShopList(shopitem : shop_item)
    fun deleteShopList(shopitem : shop_item)
    fun editShopItem(shopItem: shop_item)
    fun getItemShop(shopItemId : Int) : shop_item
    fun getShopList() : LiveData<List<shop_item>>
}