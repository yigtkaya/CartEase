package com.example.dgpays.ui.status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.dgpays.R
import com.example.dgpays.data.models.IyzicoResponse
import com.example.dgpays.databinding.FragmentFailureBinding
import com.example.dgpays.databinding.FragmentPaymentBinding
import com.example.dgpays.ui.payment.PaymentFragment
import com.example.dgpays.ui.payment.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Response

@AndroidEntryPoint
class FailureFragment(
    private val response: MutableLiveData<Response<IyzicoResponse>>
) : Fragment() {

    private val viewModel: FailureViewModel by viewModels()

    private var _binding : FragmentFailureBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFailureBinding.inflate(inflater, container, false)

        binding.tvTitle.text = "Error: "
        binding.tvDesc.text = response.value?.body()?.errorMessage

        binding.backButton.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, PaymentFragment())
                commit()
            }
        }

        return binding.root
    }
}