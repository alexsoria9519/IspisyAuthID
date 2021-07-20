package com.example.testapiipidymethods.IDS.Admin

import java.util.Date

class Session {
    var Id: String? = ""
    var AccountNumber: String? = ""
    var Amount: Double? = 0.0
    var Merchant: String? = ""
    var Type: String? = ""
    var IsNotification: Boolean? = null
    var IsForeign: Boolean? = null
    //    var CustomData:
    var Status: Int? = 0
    var Result: String = ""
    var StartDate: Date? = null
    var EndDate: Date? = null
    var Direct: Boolean? = null
    var Tag: String? = ""
}