package com.example.shoppinglist.Domain

class delete_shoplist_usecase(private val shopListRepository : shop_list_repository) {
    fun deleteShopList(shopitem : shop_item)  {
        shopListRepository.deleteShopList(shopitem)
    }
}