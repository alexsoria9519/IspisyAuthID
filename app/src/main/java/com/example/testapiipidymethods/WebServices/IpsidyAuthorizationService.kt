package com.example.testapiipidymethods.WebServices

import com.example.testapiipidymethods.IDS.Authorization.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IpsidyAuthorizationService {

    @GET("/auth/check")
    fun authCheck(): Call<Void> // return a status

    @GET("/healthcheck")
    fun checkHealth(): Call<HealthCheck>

    @POST("/notifications/check")
    fun checkInformationalNotification(
        @Body postModel: NotificationRequest
    ): Call<InformationalNotificationResult>

    @POST("/notifications/send")
    fun sendInformationalNotification(
        @Body postModel: NotificationRequest
    ): Call<InformationalNotificationResult>

    @GET("/ping")
    fun ping(): Call<BackendPingResult>

    @POST("/transactions/authorize")
    fun authorizeTransaction(
        @Body postModel: CustomerConfirmationRequest
    ): Call<AuthorizationResult>

    @POST("/transactions/begin")
    fun beginAuthorization(
        @Body postModel: AuthorizationRequest): Call<AuthorizationResult>

    @POST("/transactions/begincustom")
    fun beginCustomAuthorization(
        @Body postModel: AuthorizationRequest): Call<AuthorizationResult>

    @POST("/transactions/beginforeign")
    fun beginForeignAuthorization(
        @Body postModel: ForeignAuthorizationRequest): Call<ForeignAuthorizationResult>

    @POST("/transactions/check")
    fun checkAuthorization(
        @Body postModel: CheckAuthorizationRequest): Call<AuthorizationResult>

    @POST("/transactions/checkcustom")
    fun checkCustomAuthorization(
        @Body postModel: CheckAuthorizationRequest): Call<AuthorizationResult>

    @GET("/transactions/end/{transactionId}")
    fun endAuthorization(
        @Path(value = "transactionId",encoded = true) transactionId: String
    ): Call<AuthorizationResult>

    @GET("/transactions/endforeign/{transactionId}")
    fun endForeignAuthorization(
        @Path(value = "transactionId", encoded = true) transactionId: String
    ): Call<AuthorizationResult>

    @GET("/healthcheck")
    fun checkHealth(
        @Body postModel: HealthCheck
    ): Call<HealthCheck>

    @POST("/login")
    fun login(
        @Body postModel: CostumerInfo
    ): Call<Void>

}