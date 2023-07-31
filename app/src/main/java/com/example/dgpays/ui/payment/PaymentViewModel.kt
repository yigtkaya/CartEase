package com.example.dgpays.ui.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dgpays.data.models.IyziReq
import com.example.dgpays.data.models.IyzicoResponse
import com.example.dgpays.main.repositories.BasketRepository
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
    private val basketRepository: BasketRepository
): ViewModel() {

    var myResponse : MutableLiveData<Response<IyzicoResponse>> = MutableLiveData()
    fun pay(body: IyziReq) {
        viewModelScope.launch(dispatcher.io) {
            val result = paymentRepository.pay(body)
            myResponse.value = result
        }
    }

    fun getAllBasketItems() = basketRepository.getAllBasketItems()

    fun luhnAlgorithm(cardNumber: String): Boolean {
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
    fun isExpired(expiryDate: String): Boolean {
        if (expiryDate.isEmpty()) return false
        // create a date time today and check if the expiry date is in the past
        val month = expiryDate.substring(0, 2).toInt()
        val year = expiryDate.substring(3, 5).toInt()

        if (month > 12 || month < 1) return false

        val date = System.currentTimeMillis()
        val monthToday = date.toString().substring(4, 6).toInt()
        val yearToday = date.toString().substring(2, 4).toInt()

        return yearToday > year || (yearToday == year && monthToday > month)
    }
    fun getCardType(cardNumber: String): String {
        return when {
            isTroy(cardNumber) -> "Troy"
            isMasterCard(cardNumber) -> "MasterCard"
            isVisa(cardNumber) -> "Visa"
            isAmex(cardNumber) -> "Amex"
            else -> "Unknown"
        }
    }
    fun isCvvValid(cvv: String): Boolean {
        return cvv.length <= 4
    }
    fun isTroy(cardNumber: String): Boolean {
        return cardNumber.startsWith("9")
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

}