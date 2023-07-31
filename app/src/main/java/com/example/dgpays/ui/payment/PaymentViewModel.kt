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

}