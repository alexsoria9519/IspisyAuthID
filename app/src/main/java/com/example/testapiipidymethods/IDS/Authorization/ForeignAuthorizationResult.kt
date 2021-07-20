package com.example.testapiipidymethods.IDS.Authorization

import java.util.Date

class ForeignAuthorizationResult {
    var OneTimeSecret: String? = ""
    var TransactionId: String? = ""
    var Status: Int? = 0
    var Message: String? = ""
    var StartDate: Date? = null
    var EndDate: Date? = null
    var ConfirmationLevel: Int? = 0
//    var ConfirmationAdditionalData
}