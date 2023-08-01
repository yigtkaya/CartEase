package com.example.dgpays.ui.payment

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
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
import java.text.SimpleDateFormat
import java.util.Date
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
        try {
            viewModelScope.launch(dispatcher.io) {
                val result = paymentRepository.pay(body)
                myResponse.postValue(result)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clearBasket() = viewModelScope.launch(dispatcher.io) {
        basketRepository.deleteAllBasketItems()
    }
    fun getAllBasketItems() = basketRepository.getAllBasketItems()
    fun saveOrder(order: Order) = viewModelScope.launch(dispatcher.io) {
        orderRepository.upsertOrder(order)
    }

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

    fun isEmailValid(email: String?): Boolean {
        if (email.isNullOrEmpty()) return false
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isExpired(month: Int, year: Int): Boolean {
        return month > 12 || year < 23
    }
    fun isCvvValid(cvv: String?): Boolean {
        if (cvv.isNullOrEmpty()) return false
        return cvv.length <= 4
    }

    fun getCardProviderDrawable(cardNumber: String?): Int {
        if (cardNumber.isNullOrEmpty()) return com.example.dgpays.R.drawable.ic_mastercard_icon
        return when {
            cardNumber.startsWith("34") || cardNumber.startsWith("37") -> com.example.dgpays.R.drawable.ic_amex_icon
            cardNumber.startsWith("5") -> com.example.dgpays.R.drawable.ic_mastercard_icon
            cardNumber.startsWith("4") -> com.example.dgpays.R.drawable.ic_visa_icon
            cardNumber.startsWith("9792") -> com.example.dgpays.R.drawable.ic_troy_icon
            cardNumber.startsWith("62") -> com.example.dgpays.R.drawable.ic_paypal_icon
            else -> com.example.dgpays.R.drawable.ic_discover_icon
        }
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("MM.dd.yyyy HH:mm")
        return format.format(date)
    }
}