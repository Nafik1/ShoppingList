package com.example.shoppinglist.Domain.usecase

import androidx.lifecycle.LiveData
import com.example.shoppinglist.Domain.entity.shop_item
import com.example.shoppinglist.Domain.shop_list_repository

class get_shoplist_usecase(private val shopListRepository : shop_list_repository) {
    fun getShopList() : LiveData<List<shop_item>> {
        return shopListRepository.getShopList()
    }
}