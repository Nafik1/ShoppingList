package com.example.shoppinglist.Domain

import androidx.lifecycle.LiveData
import com.example.shoppinglist.Domain.entity.shop_item

interface shop_list_repository {
    suspend fun addShopList(shopitem : shop_item)
    suspend fun deleteShopList(shopitem : shop_item)
    suspend fun editShopItem(shopItem: shop_item)
    suspend fun getItemShop(shopItemId : Int) : shop_item
    fun getShopList() : LiveData<List<shop_item>>
}