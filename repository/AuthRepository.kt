package ru.dvizh.client.domain.repository

import ru.dvizh.client.data.models.auth.RequestOtpDTO
import ru.dvizh.client.data.models.auth.SendOtpDTO
import ru.dvizh.client.data.models.user.UserDTO
import ru.efremovkirill.ktorhandler.Response

interface AuthRepository {

    suspend fun requestOtp(requestOtpDTO: RequestOtpDTO): Response<String?>
    suspend fun sendOtp(sendOtpDTO: SendOtpDTO): Response<UserDTO?>

}