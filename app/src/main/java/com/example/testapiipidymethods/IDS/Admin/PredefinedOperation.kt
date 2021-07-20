package com.example.testapiipidymethods.IDS.Admin

import java.util.ArrayList

class PredefinedOperation {
    var ExternalId: String? = ""
    var CustomerName: String? = ""
    var CustomerNumber: String? = ""
    var Name: String? = ""
    var Description: String? = ""
    var Resources = ArrayList<OperationResource>()
    var Parameters = ArrayList<OperationParameter>()
}