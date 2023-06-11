package ru.dvizh.client.presentation.auth

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.dvizh.client.domain.constants.Constants.CODE_OK
import ru.dvizh.client.domain.models.auth.RequestOtp
import ru.dvizh.client.domain.usecases.auth.RequestOtpUseCase
import ru.dvizh.client.presentation.BaseViewModel
import ru.dvizh.client.presentation.utils.UIState

class AuthViewModel : BaseViewModel() {

    private val requestOtpUseCase = RequestOtpUseCase()

    private val _requestOtp: MutableSharedFlow<UIState<Boolean>> = MutableSharedFlow()
    val requestOtp = _requestOtp.asSharedFlow()

    fun requestOtp(phone: String, isTermsAgreed: Boolean) = vms.launch(dio) {
        _requestOtp.loading()
        delay(1000L)

        if (phone.length != 10) return@launch _requestOtp.error(message = WRONG_PHONE_FORMAT)
        if (!isTermsAgreed) return@launch _requestOtp.error(message = WRONG_PHONE_FORMAT)

        _requestOtp.success(data = true)
//        _requestOtp.idle()
        val requestOtp = RequestOtp(phone = phone)
        val response = requestOtpUseCase(requestOtp = requestOtp)

        if (response.code == CODE_OK)
            _requestOtp.success(data = true)
        else
            _requestOtp.error(message = response.message)
    }

    companion object {
        private const val TERMS_NOT_AGREED = "Необходимо прочитать и принять лицензионное соглашение и политику конфиденциальности"
        private const val WRONG_PHONE_FORMAT = "Неверный формат номера"
    }

}