package com.example.shoppinglist.Domain.usecase

import com.example.shoppinglist.Domain.entity.shop_item
import com.example.shoppinglist.Domain.shop_list_repository

class edit_shopitem_usecase(private val shopListRepository : shop_list_repository) {
    suspend fun editShopItem(shopItem: shop_item) {
        shopListRepository.editShopItem(shopItem)
    }
}