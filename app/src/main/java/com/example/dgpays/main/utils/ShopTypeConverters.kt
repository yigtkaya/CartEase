package com.example.dgpays.main.utils

import androidx.room.TypeConverter
import com.example.dgpays.data.models.MarketItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ShopTypeConverters{

    @TypeConverter
    fun fromString(value: String): MutableList<MarketItem> {
        val type = object : TypeToken<MutableList<MarketItem>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromList(list: MutableList<MarketItem>): String {
        return Gson().toJson(list)
    }

}