package com.example.testapiipidymethods.IDS.Accounts

import java.util.*

class BiometricCredential {
    var Id: String ? = ""
    var ExternalId: String = ""
    var Description: String? = ""
    var CredentialType: Int? = 0// IDS.IDComplete.Common.Model.BiometricCredentialType
    var DataType: Int? = 0 // IDS.IDComplete.Common.Model.BiometricDataType
//    var Data : Array<Byte?> = emptyArray()
    var Data : String? = ""
    var IsProofed: Boolean? = null
    //    var SourceCredentials:  Array of JSON object (IDS.IDComplete.Common.Model.BiometricCredential[])
    var CreatedDate: Date? = null
    var DeletedDate: Date? = null
}