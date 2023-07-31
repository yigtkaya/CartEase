package com.example.dgpays.ui.status

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dgpays.databinding.FragmentPaymentBinding
import com.example.dgpays.ui.payment.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuccessFragment : Fragment() {

    private val viewModel: SuccesViewModel by viewModels()

    private var _binding : FragmentPaymentBinding? = null
    private val binding get() = _binding!!
}