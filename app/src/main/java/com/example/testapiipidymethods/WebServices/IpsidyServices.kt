package com.example.testapiipsidy.WebServices

import retrofit2.http.GET

interface IpsidyServices {

    @GET("/adminUser/actions")
    fun getAdministrativeUserActions()





}