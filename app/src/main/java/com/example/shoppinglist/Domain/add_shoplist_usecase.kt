package com.example.shoppinglist.Domain

class add_shoplist_usecase(private val shopListRepository : shop_list_repository) {
    fun addShopList(shopitem : shop_item) {
        shopListRepository.addShopList(shopitem)
    }
}