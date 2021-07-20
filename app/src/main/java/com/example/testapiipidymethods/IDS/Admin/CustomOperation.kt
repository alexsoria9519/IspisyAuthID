package com.example.testapiipidymethods.IDS.Admin

import java.util.ArrayList
import java.util.Date

class CustomOperation {
    var ExternalId: String? = ""
    var CustomerName: String? = ""
    var CustomerNumber: String? = ""
    var Name: String? = ""
    var Description: String? = ""
    var Enabled: Boolean? = null
    var IsNotification: Boolean? = null
    var Parameters = ArrayList<OperationParameter>()
    var Resources = ArrayList<OperationResource>()
    var CreatedDate: Date? = null
    var DeletedDate: Date? = null
    var IsForeign: Boolean? = null
}