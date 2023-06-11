package ru.dvizh.client.domain.usecases.auth

import ru.dvizh.client.data.repository.auth.AuthRepositoryImpl
import ru.dvizh.client.domain.models.auth.RequestOtp
import ru.dvizh.client.domain.repository.AuthRepository
import ru.efremovkirill.ktorhandler.Response

class RequestOtpUseCase(private val repository: AuthRepository = AuthRepositoryImpl()) {

    suspend operator fun invoke(requestOtp: RequestOtp): Response<String?> =
        repository.requestOtp(requestOtpDTO = requestOtp.toDTO())

}