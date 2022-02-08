package com.example.testapiipidymethods.WebServices

import com.example.testapiipidymethods.IDS.Request
import com.example.testapiipidymethods.IDS.Accounts.*
import com.example.testapiipidymethods.IDS.Authorization.CostumerInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IpsidyAccountServices {

    @GET("/accountList?filter={filter}&sort={sort}&start={start}&size={size}")
    fun getAccountList(
        @Path(value = "name", encoded = true) name: String,
        @Path(value = "name", encoded = true) sort: String,
        @Path(value = "name", encoded = true) start: String,
        @Path(value = "name", encoded = true) size: String
    ): Call<Account>

    @POST("accounts")
    suspend  fun createAccount(
        @Body postModel: Account
    ): Response<Account>

    @GET("/accounts/{accountNumber}")
    fun getAccount(
        @Path(value = "accountNumber", encoded = true) accountNumber: String
    ): Call<Account>

    @PUT("/accounts/{accountNumber}")
    fun updateAccount(
        @Path(value = "accountNumber", encoded = true) accountNumber: String
    ): Call<Account>

    @DELETE("/accounts/{accountNumber}")
    fun deleteAccount(
        @Path(value = "accountNumber", encoded = true) accountNumber: String
    ): Call<Account>

    @POST("accounts/{accountNumber}/bioCredential")
    suspend  fun createAccountBiometricCredential(
        @Path(value = "accountNumber", encoded = true) accountNumber: String,
        @Body postModel: BiometricCredential
    ): Response<BiometricCredential>

    @DELETE("/accounts/{accountNumber}/bioCredential")
    fun deleteAccountBiometricCredential(
        @Path(value = "accountNumber", encoded = true) accountNumber: String
    ): Call<Void>

    @DELETE("/accounts/{accountNumber}/bioCredential/rawdata")
    fun deleteSingleAccountBiometricCredentialRawData(
        @Path(value = "accountNumber", encoded = true) accountNumber: String
    ) : Response<Int>

    @POST("/accounts/{accountNumber}/bioCredential/verify")
    fun verifyAccountBiometricCredential(
        @Path(value = "accountNumber", encoded = true) accountNumber: String,
        @Body postModel: BiometricCredential
    ) : Response<Boolean>

    @POST("accounts/{accountNumber}/bioCredential/verify2")
    suspend fun verifyAccountBiometricCredential2(
        @Path(value = "accountNumber", encoded = true) accountNumber: String,
        @Body postModel: BiometricCredential
    ): Response<BiometricVerificationResult>

    @POST("/accounts/{accountNumber}/enable?enabled={enabled}&reason={reason}")
    fun enableAccount(
        @Path(value = "accountNumber", encoded = true) accountNumber: String,
        @Body postModel: EnableAccount
    ) : Call<Void>

    @POST("/accounts/{accountNumber}/externalLink")
    fun createAccountExternalLink(
        @Path(value = "accountNumber", encoded = true) accountNumber: String,
        @Body postModel: ExternalLink
    ) : Call<Void>


    @GET("/accounts/{accountNumber}/policy")
    fun getPolicy(
        @Path(value = "accountNumber",encoded = true) accountNumber: String
    ): Call<Policy>

    @PUT("/accounts/{accountNumber}/policy")
    fun updatePolicy(
        @Path(value = "accountNumber", encoded = true) accountNumber: String,
        @Body postModel: Policy
    ) : Call<Void>

    @POST("/accounts/{accountNumber}/preRegistration")
    fun createPreRegistration(
        @Path(value = "accountNumber", encoded = true) accountNumber: String,
        @Body postModel: PreRegistration
    ): Call<PreRegistration>

    @GET("/accounts/{accountNumber}/preRegistrations")
    fun getPreRegistrations(
        @Path(value = "accountNumber",encoded = true) accountNumber: String
    ): Call<PreRegistration> // Array PreRegistration

    @POST("/accounts/{accountNumber}/syncFromExternalSystem")
    fun syncFromExternalSystem(
        @Path(value = "accountNumber",encoded = true) accountNumber: String
    ) : Call<Void>

    @POST("/accounts/{accountNumber}/syncToExternalSystem")
    fun syncToExternalSystem(
        @Path(value = "accountNumber",encoded = true) accountNumber: String
    ) : Call<Void>

    @POST("/accounts/{accountNumber}/unlink?username={username}")
    fun unlinkAccount(
        @Path(value = "accountNumber", encoded = true) accountNumber: String,
        @Query(value = "username", encoded = true) username: String
    ) : Call<Void>

    @GET("/accounts/bioCredentials/metadata?start={start}&size={size}")
    fun getAccountBiometricCredentialsMetadata(
        @Query(value = "start", encoded = true) start: String,
        @Query(value = "size", encoded = true) size: String
    ): Call<BiometricCredentialMetadataResult>

    @DELETE("/accounts/bioCredentials/rawdata")
    fun deleteAllAccountBiometricCredentialRawData() : Response<Int>

    @GET("/accounts/bioCredentials/rawdata/count")
    fun getAccountBiometricCredentialRawDataCount() : Response<Int>

    @GET("/accounts?filter={filter}&sort={sort}&start={start}&size={size}")
    fun getAccounts(
        @Query(value = "filter", encoded = true) filter: String,
        @Query(value = "sort", encoded = true) sort: String,
        @Query(value = "start", encoded = true) start: String,
        @Query(value = "size", encoded = true) size: String
    ): Call<AccountResult>

    @GET("/audit/accounts/{uin}")
    fun getAccountByUIN(
        @Path(value = "externalId", encoded = true) externalId: String
    ): Call<Account>

    @GET("/audit/preRegistrations/{uin}")
    fun getPreRegistrationsByUIN(
        @Path(value = "uin", encoded = true) uin: String
    ): Call<PreRegistration>

    @GET("/bioCredentials/{externalId}")
    fun getBiometricCredential(
        @Path(value = "externalId", encoded = true) externalId: String
    ): Call<BiometricCredential>

    @GET("/checkliveness")
    fun checkLiveness(@Body postModel: CheckLivenessRequest): Call<LivenessDetectionResult>

    @GET("/customer/operations/externalVerifyDocument")
    fun externalVerifyIDDocument()

    @GET("defaultPolicy")
    fun getDefaultPolicy() : Call<Policy>

    @PUT("/defaultPolicy")
    fun updateDefaultPolicy(
        @Body postModel: PolicyRequest
    ) : Call<Void>

    @GET("/preRegistrations/{uin}")
    fun getPreRegistration(
        @Path(value = "uin", encoded = true) uin: String
    ): Call<PreRegistration>

    @POST("/preRegistrations/{uin}/cancel")
    fun cancelPreRegistration(
        @Path(value = "uin", encoded = true) uin: String
    ): Call<Void>

    @POST("login")
    fun login(): Call<CostumerInfo>

}