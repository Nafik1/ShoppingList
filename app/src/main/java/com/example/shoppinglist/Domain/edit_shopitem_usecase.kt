package com.example.shoppinglist.Domain

class edit_shopitem_usecase(private val shopListRepository : shop_list_repository) {
    fun editShopItem(shopItem: shop_item) {
        shopListRepository.editShopItem(shopItem)
    }
}