package com.example.dgpays.ui.bag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dgpays.data.models.Basket
import com.example.dgpays.main.repositories.BasketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingBagViewModel @Inject constructor(
    private val repository: BasketRepository
): ViewModel() {

    fun getAllBasketItems() = repository.getAllBasketItems()

    fun deleteAllBasketItems() {
        viewModelScope.launch {
            repository.deleteAllBasketItems()
        }
    }

}