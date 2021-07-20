package com.example.testapiipidymethods.IDS.Authorization

import java.util.ArrayList

class HealthCheck {
    var Status: String? = ""
    var Message: String = ""
    var Dependencies = ArrayList<HealthCheckResult>()
}