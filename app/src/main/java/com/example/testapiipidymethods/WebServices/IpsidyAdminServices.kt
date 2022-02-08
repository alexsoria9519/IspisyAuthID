package com.example.testapiipidymethods.WebServices


import com.example.testapiipidymethods.IDS.Accounts.BiometricCredential
import com.example.testapiipidymethods.IDS.Admin.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface IpsidyAdminServices {


    @GET("/adminUser/actions")
    fun getAdministrativeUserActions(): Call<CustomerRoleAction> // returns Array  CustomerRoleAction

    @POST("/adminUser/apiKeys")
    fun createApiKey(@Body postModel: ApiKeyRequest): Call<ApiKey>

    @GET("adminUser/apiKeys")
//    fun getApiKeys(): Call<List<ApiKeyLite>> // returns Array ApiKeyLite
    fun getApiKeys(): Call<ArrayList<ApiKeyLite>> // returns Array ApiKeyLite

    @GET("/adminUser/apiKeys/{apiKeyExternalId}/revokeRefeshToken")
    fun revokeApiKeyRefreshToken(
        @Path(value = "apiKeyExternalId", encoded = true) apiKeyExternalId: String
    ): Response<Boolean>

    @GET("/adminUser/apiKeys/{externalId}")
    fun getApiKey(@Path(value = "externalId", encoded = true) externalId: String): Call<ApiKeyLite>

    @DELETE("/adminUser/apiKeys/{externalId}")
    fun deleteApiKey(
        @Path(value = "externalId",encoded = true) externalId: String
    ): Response<Boolean> // returns a Boolean

    @POST("/adminUser/apiKeys/{externalId}/enable?enabled={enabled}&reason={reason}")
    fun enableApiKey(
        @Path(value = "externalId", encoded = true) externalId: String,
        @Query(value = "enabled", encoded = true) enabled: String,
        @Query(value = "reason", encoded = true) reason: String
    ) :Call<Void>

    @POST("/adminUser/changePassword")
    fun changeAdministrativeUserPassword(
        @Body postModel: ChangePasswordRequest
    ) : Call<Void>

    @POST("/attributes")
    fun createCustomerAttribute(
        @Body postModel: CustomerAttributeRequest
    ): Call<CustomerAttribute>

    @PUT("/attributes/{externalId}")
    fun updateCustomerAttribute(
        @Path(value = "externalId", encoded = true) externalId: String,
        @Body postModel: CustomerAttributeRequest
    ) : Call<Void>

    @DELETE("/attributes/{externalId}")
    fun deleteCustomerAttribute(
        @Path(value = "externalId",encoded = true) externalId: String
    ): Response<Boolean>

    @GET("/attributes?externalId={externalId}&name={name}&locale={locale}")
    fun getCustomerAttributes(
        @Query(value = "externalId", encoded = true) externalId: String,
        @Query(value = "name", encoded = true) name: String,
        @Query(value = "locale", encoded = true) locale: String
    ): Call<CustomerAttribute> // return Array CustomerAttribute

    @GET("/audit/apiKeys/{externalId}")
    fun getApiKeyByExernalId(
        @Path(value = "externalId", encoded = true) externalId: String
    ): Call<ApiKeyLite>

    @GET("/audit/attributes/{externalId}")
    fun getCustomerAttributeByExternalId(
        @Path(value = "externalId", encoded = true) externalId: String
    ): Call<CustomerAttribute>

    @GET("/audit/bioCredentials/{externalId}")
    fun getBiometricCredentialByExternalId(
        @Path(value = "externalId", encoded = true) externalId: String
    ): Call<BiometricCredential>

    @GET("/audit/customOperationResources/{resourceId}")
    fun getCustomOperationResourceByExternalId(
        @Path(value = "resourceId",encoded = true) resourceId: String
    ): Call<OperationResource>

    @GET("/audit/customOperations/{operationId}")
    fun getCustomOperationByExternalId(
        @Path(value = "operationId",encoded = true) operationId: String
    ): Call<CustomOperation>

    @GET("/audit/events?filter={filter}&sort={sort}&start={start}&size={size}")
    fun GetSystemAuditEvents(
        @Query(value = "filter", encoded = true) filter: String,
        @Query(value = "sort", encoded = true) sort: String,
        @Query(value = "start", encoded = true) start: String,
        @Query(value = "size", encoded = true) size: String
    ): Call<AuditEventsResult>

    @GET("/audit/predefinedOperationResources/{resourceId}")
    fun getPredefinedOperationResourceByExternalId(
        @Path(value = "resourceId",encoded = true) resourceId: String
    ): Call<OperationResource>

    @GET("/audit/predefinedOperations/{operationId}")
    fun getPredefinedOperationByExternalId(
        @Path(value = "operationId",encoded = true) operationId: String
    ): Call<PredefinedOperation>

    @GET("/auth/check")
    fun authCheck() : Call<Void>

    @POST("/customOperations")
    fun createCustomOperation(
        @Body postModel: CustomOperationRequest
    ): Call<CustomOperation>

    @GET("/customOperations")
    fun getCustomOperations(): Call<CustomOperation> // return Array CustomOperation

    @GET("/customOperations/{operationId}")
    fun getCustomOperation(
        @Path(value = "operationId",encoded = true) operationId: String
    ): Call<CustomOperation>

    @PUT("/customOperations/{operationId}")
    fun updateCustomOperation(
        @Path(value = "operationId", encoded = true) operationId: String,
        @Body postModel: CustomOperation
    ) : Call<Void>

    @DELETE("/customOperations/{operationId}")
    fun deleteCustomOperation(
        @Path(value = "operationId", encoded = true) operationId: String
    ): Response<Boolean>

    @POST("/customOperations/{operationId}/resources")
    fun createCustomOperationResource(
        @Path(value = "operationId", encoded = true) operationId: String,
        @Body postModel: OperationResource
    ): Call<OperationResource>

    @GET("/customOperations/{operationId}/resources")
    fun getCustomOperationResources(
        @Path(value = "operationId", encoded = true) operationId: String
    ): Call<OperationResource> // return Array OperationResource

    @GET("/customOperations/{operationId}/resources/{resourceId}")
    fun getCustomOperationResource(
        @Path(value = "operationId", encoded = true) operationId: String,
        @Path(value = "resourceId", encoded = true) resourceId: String
    ): Call<OperationResource>

    @PUT("/customOperations/{operationId}/resources/{resourceId}")
    fun updateCustomOperationResource(
        @Path(value = "operationId", encoded = true) operationId: String,
        @Path(value = "resourceId", encoded = true) resourceId: String
    ): Call<OperationResource>

    @DELETE("/customOperations/{operationId}/resources/{resourceId}")
    fun deleteCustomOperationResource(
        @Path(value = "operationId", encoded = true) operationId: String,
        @Path(value = "resourceId", encoded = true) resourceId: String
    ): Call<Void>


    @GET("/ping")
    fun ping(): Call<BackendPingResult>

    @GET("/predefinedOperations")
    fun getPredefinedOperations(): Call<PredefinedOperation> // Return Array PredefinedOperation

    @POST("/predefinedOperations/{operationId}/resources")
    fun createPredefinedOperationResource(
        @Path(value = "operationId", encoded = true) operationId: String,
        @Body postModel: OperationResource
    ): Call<OperationResource>

    @GET("/predefinedOperations/{operationId}/resources")
    fun getPredefinedOperationResources(
        @Path(value = "operationId", encoded = true) operationId: String
    ): Call<OperationResource> // return Array OperationResource

    @GET("/predefinedOperations/{operationId}/resources/{resourceId}")
    fun getPredefinedOperationResource(
        @Path(value = "operationId", encoded = true) operationId: String,
        @Path(value = "resourceId", encoded = true) resourceId: String
    ): Call<OperationResource>

    @PUT("/predefinedOperations/{operationId}/resources/{resourceId}")
    fun updatePredefinedOperationResource(
        @Path(value = "operationId", encoded = true) operationId: String,
        @Body postModel: OperationResource
    ): Call<Void>

    @DELETE("/predefinedOperations/{operationId}/resources/{resourceId}")
    fun deletePredefinedOperationResource(
        @Path(value = "operationId", encoded = true) operationId: String,
        @Path(value = "resourceId", encoded = true) resourceId: String
    ): Response<Boolean>


    @GET("/searchMetadata")
    fun getSearchMetadata() : Response<String>

    @GET("/settings/generic")
    fun getCustomerGenericSettings(): Call<CustomerGenericSettings>

    @PUT("/settings/generic")
    fun updateCustomerGenericSettings(
        @Body postModel: CustomerGenericSettings
    ): Call<Void>

    @GET("/settings/webhook")
    fun getCustomerWebhookSettings(): Call<WebHookSettings>

    @PUT("/settings/webhook")
    fun updateCustomerWebhookSettings(
        @Body postModel: WebHookSettings
    ): Call<WebHookSettings>

    @POST("/settings/webhook/secret")
    fun resetCustomerWebhookSecret() : Call<WebHookSettings>

    @POST("/settings/webhook/test?eventType={eventType}")
    fun testCustomerWebhook(
        @Query(value = "eventType", encoded = true) eventType: Int,
        @Body postModel: WebHookSettings
    ): Call<Void>

    @GET("/transactionList?filter={filter}&sort={sort}&start={start}&size={size}")
    fun getTransactionList(
        @Query(value = "filter", encoded = true) filter: String,
        @Query(value = "sort", encoded = true) sort: String,
        @Query(value = "start", encoded = true) start: Int,
        @Query(value = "size", encoded = true) size: Int
    ): Call<TransactionResponse>

    @GET("/transactions/{transactionId}/confirmation")
    fun getCustomerTransactionConfirmation(
        @Path(value = "transactionId", encoded = true) transactionId: String
    ): Call<CustomerInformation>

    @GET("/transactions?filter={filter}&sort={sort}&start={start}&size={size}")
    fun getTransactions(
        @Query(value = "filter", encoded = true) filter: String,
        @Query(value = "sort", encoded = true) sort: String,
        @Query(value = "start", encoded = true) start: Int,
        @Query(value = "size", encoded = true) size: Int
    ): Call<SessionResponse>













}