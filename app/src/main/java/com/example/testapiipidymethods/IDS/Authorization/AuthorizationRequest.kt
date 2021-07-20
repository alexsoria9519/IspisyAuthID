package com.example.testapiipidymethods.IDS.Authorization

import java.text.DecimalFormat

class AuthorizationRequest {
    var AccountNumber: String? = ""
    var Amount: DecimalFormat? = null
    var Merchant: String = ""
    var ConfirmationLevel: Int? = 0
    var Direct: Boolean? = null
    var CheckLiveness: Boolean? = null
    var Tag: String? = ""
    var OperationName: String = ""
}