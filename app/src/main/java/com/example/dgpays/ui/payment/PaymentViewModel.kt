package com.example.dgpays.ui.payment

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dgpays.data.models.IyziReq
import com.example.dgpays.data.models.IyzicoResponse
import com.example.dgpays.data.models.Order
import com.example.dgpays.main.repositories.BasketRepository
import com.example.dgpays.main.repositories.OrderRepository
import com.example.dgpays.main.repositories.PaymentRepositoryImpl
import com.example.dgpays.main.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepositoryImpl,
    private val dispatcher: DispatcherProvider,
    private val basketRepository: BasketRepository,
    private val orderRepository: OrderRepository
): ViewModel() {

    var myResponse : MutableLiveData<Response<IyzicoResponse>> = MutableLiveData()
    fun pay(body: IyziReq) {
        viewModelScope.launch(dispatcher.io) {
            val result = paymentRepository.pay(body)
            myResponse.postValue(result)
        }
    }

    fun clearBasket() = viewModelScope.launch(dispatcher.io) {
        basketRepository.deleteAllBasketItems()
    }
    fun getAllBasketItems() = basketRepository.getAllBasketItems()

    fun luhnAlgorithm(cardNumber: String?): Boolean {
        if (cardNumber.isNullOrEmpty()) return false
        var sum = 0
        var alternate = false
        for (i in cardNumber.length - 1 downTo 0) {
            var n = Integer.parseInt(cardNumber.substring(i, i + 1))
            if (alternate) {
                n *= 2
                if (n > 9) {
                    n = n % 10 + 1
                }
            }
            sum += n
            alternate = !alternate
        }
        return sum % 10 == 0
    }
    fun isExpired(month: Int, year: Int): Boolean {
        return month > 12 || year < 23
    }
    fun isCvvValid(cvv: String?): Boolean {
        if (cvv.isNullOrEmpty()) return false
        return cvv.length <= 4
    }

    fun isMasterCard(cardNumber: String): Boolean {
        return cardNumber.startsWith("5")
    }
    fun isVisa(cardNumber: String): Boolean {
        return cardNumber.startsWith("4")
    }
    fun isAmex(cardNumber: String): Boolean {
        return cardNumber.startsWith("34") || cardNumber.startsWith("37")
    }

    fun saveOrder(order: Order) = viewModelScope.launch(dispatcher.io) {
        orderRepository.upsertOrder(order)
    }

}