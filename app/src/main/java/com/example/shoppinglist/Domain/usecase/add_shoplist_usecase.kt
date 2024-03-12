package com.example.shoppinglist.Domain.usecase

import com.example.shoppinglist.Domain.entity.shop_item
import com.example.shoppinglist.Domain.shop_list_repository

class add_shoplist_usecase(private val shopListRepository : shop_list_repository) {
    suspend fun addShopList(shopitem : shop_item) {
        shopListRepository.addShopList(shopitem)
    }
}