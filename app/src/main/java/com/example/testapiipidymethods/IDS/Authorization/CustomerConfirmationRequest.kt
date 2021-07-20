package com.example.testapiipidymethods.IDS.Authorization

class CustomerConfirmationRequest {
    var Id: String? = ""
    var Result: String = ""
    var PincodeHash: Array<Byte> = emptyArray()
    var BiometricDataType: Int? = 0
    var BiometricData: Array<Byte> = emptyArray()
}