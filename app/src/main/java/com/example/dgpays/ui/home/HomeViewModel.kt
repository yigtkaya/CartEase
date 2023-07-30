package com.example.dgpays.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dgpays.data.models.Basket
import com.example.dgpays.data.models.MarketItem
import com.example.dgpays.main.repositories.BasketRepository
import com.example.dgpays.main.repositories.MarketItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val marketItemRepository: MarketItemRepository,
    private val basketRepository: BasketRepository
): ViewModel() {

    fun upsertMarketItem(itemList:List<MarketItem>) = viewModelScope.launch {
        itemList.forEach {
            marketItemRepository.upsertMarketItem(it)
        }
    }
    fun getAllMarketItems() = marketItemRepository.getAllMarketItems()

    fun getAllBasketItems() = basketRepository.getAllBasketItems()

    fun upsertBasketItem(basket: Basket) = viewModelScope.launch {
        basketRepository.upsertBasketItem(basket)
    }

    fun deleteBasketItem(id: Int) = viewModelScope.launch {
        basketRepository.deleteBasketItem(id)
    }

    fun updateBasketItem(basket: Basket) = viewModelScope.launch {
        basketRepository.updateBasketItemAmount(basket.id, basket.amount)
    }
}