package com.example.testapiipidymethods.IDS.Admin

import java.util.ArrayList
import java.util.Date

class SystemAudit {
    var Id: Int? = 0
    var EventLevel: Int? = 0
    var Message: String? = ""
    var MessageType: Int? = 0
    var EventDate: Date? = null
    var EventSource: String? = ""
    var HostName: String? = ""
    var ExecutorType: Int? = 0
    var ExecutorExternalId: String? = ""
    var ExecutorValue: String? = ""
    var ExecutorRoles: String? = ""
    var References = ArrayList<SystemAuditReference>()
}