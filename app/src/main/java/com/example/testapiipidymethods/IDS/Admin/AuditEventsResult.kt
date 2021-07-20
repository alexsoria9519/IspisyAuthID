package com.example.testapiipidymethods.IDS.Admin

import com.example.testapiipidymethods.IDS.Admin.SystemAudit
import java.util.ArrayList

class AuditEventsResult {
    var Items = ArrayList<SystemAudit>()
    var TotalCount: Int? = 0
}