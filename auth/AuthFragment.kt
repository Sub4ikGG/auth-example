package ru.dvizh.client.presentation.auth

import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.dvizh.client.R
import ru.dvizh.client.databinding.FragmentAuthBinding
import ru.dvizh.client.domain.constants.Constants
import ru.dvizh.client.presentation.BaseFragment
import ru.dvizh.client.presentation.MainActivity
import ru.dvizh.client.presentation.utils.UIState
import ru.dvizh.client.presentation.utils.hideKeyboard
import ru.dvizh.client.presentation.webview.WebViewBottomSheet

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectRequestOtp()
        applyToolbar()
        setOnClickListeners()
        configurePhoneLayout()
    }

    private fun applyToolbar() {
        binding.toolbar.titleTextView.text = "Введите номер"
    }

    private fun setOnClickListeners() {
        binding.authButton.button.setOnClickListener {
            val phone = binding.phoneLayout.editText.unMaskedText.toString()
            val isTermsAgreed = binding.termsAgreeSwitch.checkbox.isChecked

            viewModel.requestOtp(phone = phone, isTermsAgreed = isTermsAgreed)
        }

        binding.licenseTextView.setOnClickListener {
            val webViewBottomSheet = WebViewBottomSheet(Constants.DOCUMENTS_URL)
            webViewBottomSheet.show(childFragmentManager, WebViewBottomSheet.TAG)
        }
    }

    private fun collectRequestOtp() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.requestOtp.collect { state ->
                when (state) {
                    is UIState.Success -> {
                        val phone = binding.phoneLayout.editText.unMaskedText.toString()

                        (requireActivity() as MainActivity).hideLoading()
                        findNavController().navigate(
                            R.id.action_authFragment_to_otpFragment,
                            getBundleWithPhone(phone = phone)
                        )
                    }

                    is UIState.Loading -> {
                        hideKeyboard()
                        (requireActivity() as MainActivity).showLoading()
                    }

                    is UIState.Error -> {
                        (requireActivity() as MainActivity).hideLoading()
                        showWarning(message = state.message ?: "")
                    }

                    is UIState.Idle -> {}
                }
            }
        }
    }

    private fun configurePhoneLayout() {
        binding.phoneLayout.editText.inputType = InputType.TYPE_CLASS_PHONE
        binding.phoneLayout.editText.setMask(PHONE_MASK)
        binding.phoneLayout.editText.keyListener = DigitsKeyListener.getInstance(ACCEPTED_DIGITS)
    }

    private fun getBundleWithPhone(phone: String): Bundle {
        val bundle = Bundle()
        bundle.putString(PHONE, phone)
        return bundle
    }

    override fun getViewBinding(): FragmentAuthBinding {
        return FragmentAuthBinding.inflate(layoutInflater)
    }

    companion object {
        const val PHONE = "phone"

        const val PHONE_MASK = "+7 (###) ###-##-##"
        const val ACCEPTED_DIGITS = "0123456789"
    }
}