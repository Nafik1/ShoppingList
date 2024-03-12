package com.example.shoppinglist.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopItems")
data class ShopItemDBModel (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name: String,
    val count: Int,
    val status: Boolean
)
