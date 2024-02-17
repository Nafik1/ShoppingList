package com.example.shoppinglist.Domain

class get_shopitem_usecase(private val shopListRepository : shop_list_repository) {
    fun getItemShop(shopItemId : Int) : shop_item {
        return shopListRepository.getItemShop(shopItemId)
    }
}