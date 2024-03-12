package com.example.shoppinglist.Domain.usecase

import com.example.shoppinglist.Domain.entity.shop_item
import com.example.shoppinglist.Domain.shop_list_repository

class delete_shoplist_usecase(private val shopListRepository : shop_list_repository) {
    suspend fun deleteShopList(shopitem : shop_item)  {
        shopListRepository.deleteShopList(shopitem)
    }
}