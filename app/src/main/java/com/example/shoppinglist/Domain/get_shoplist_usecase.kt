package com.example.shoppinglist.Domain

class get_shoplist_usecase(private val shopListRepository : shop_list_repository) {
    fun getShopList() : List<shop_item> {
        return shopListRepository.getShopList()
    }
}