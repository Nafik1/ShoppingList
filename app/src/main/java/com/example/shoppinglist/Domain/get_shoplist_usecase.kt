package com.example.shoppinglist.Domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class get_shoplist_usecase(private val shopListRepository : shop_list_repository) {
    fun getShopList() : LiveData<List<shop_item>> {
        return shopListRepository.getShopList()
    }
}