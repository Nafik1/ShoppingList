package com.example.shoppinglist.Domain.usecase

import com.example.shoppinglist.Domain.entity.shop_item
import com.example.shoppinglist.Domain.shop_list_repository

class get_shopitem_usecase(private val shopListRepository : shop_list_repository) {
    suspend fun getItemShop(shopItemId : Int) : shop_item {
        return shopListRepository.getItemShop(shopItemId)
    }
}