package com.example.dgpays.ui.status

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
@HiltViewModel
class SuccesViewModel @Inject constructor() : ViewModel() {

    fun convertLongToDate(timestamp: Long): Date {
        return Date().apply {
            time = timestamp
        }
    }
}