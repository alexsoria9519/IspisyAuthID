package com.example.testapiipidymethods.IDS.Admin

import com.example.testapiipidymethods.IDS.Accounts.BiometricCredential
import com.example.testapiipidymethods.IDS.Accounts.LivenessDetectionResult
import java.util.Date

class CustomerInformation {
    var ExternalId: String = ""
    var ConfirmationStatus: Int? = 0
    var ConfirmationAttempts: Int? = 0
    var Timeout: String? = ""
    var ConfirmationType: Int? = 0
    var NotificationSendingDate: Date? = null
    var ConfirmationEndDate: Date? = null
    var UserName: String? = ""
    var ConfirmationLevel: Int? = 0
    var EthalonBiometricCredential: BiometricCredential? = null
    var CandidateBiometricCredential: BiometricCredential? = null
    var Geofencing: Geofencing? = null
    var BiometricScore: Double? = 0.0
    var BiometricMatchProbability: Double? = 0.0
    var CheckLiveness: Boolean? = null
    var LivenessDetectionResult: LivenessDetectionResult? = null
}