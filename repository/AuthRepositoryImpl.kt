package ru.dvizh.client.data.repository.auth

import io.ktor.client.call.*
import io.ktor.http.*
import ru.dvizh.client.data.models.auth.RequestOtpDTO
import ru.dvizh.client.data.models.auth.SendOtpDTO
import ru.dvizh.client.data.models.user.UserDTO
import ru.dvizh.client.domain.repository.AuthRepository
import ru.efremovkirill.ktorhandler.KtorClient
import ru.efremovkirill.ktorhandler.Response

class AuthRepositoryImpl: AuthRepository {
    override suspend fun requestOtp(requestOtpDTO: RequestOtpDTO): Response<String?> {
        return KtorClient.post(path = "/auth", body = requestOtpDTO)
            .takeIf { it?.status?.isSuccess() == true }?.body() ?: Response.empty()
    }

    override suspend fun sendOtp(sendOtpDTO: SendOtpDTO): Response<UserDTO?> {
        return KtorClient.post(
            path = "/auth",
            body = sendOtpDTO
        ).takeIf { it?.status?.isSuccess() == true }?.body() ?: Response.empty()
    }
}