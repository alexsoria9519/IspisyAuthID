package com.example.testapiipidymethods.Methods
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapiipidymethods.IDS.Admin.ApiKeyLite
import java.util.ArrayList
import com.example.testapiipidymethods.R

class GetApisAdapter(private var dataApikeys:  ArrayList<ApiKeyLite>):  RecyclerView.Adapter<GetApisAdapter.GetAPisViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GetAPisViewHolder {
        return GetAPisViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.get_apis_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataApikeys.size
    }

    override fun onBindViewHolder(holder: GetAPisViewHolder, position: Int) {
        val item = dataApikeys.get(position)
        holder.bindView(item)
    }


    class GetAPisViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val externalIdtv: TextView
        val administrativeUserLogintv: TextView
        val customerNumbertv: TextView
        val descriptiontv: TextView
        val enabledtv: TextView
        val disableReasontv: TextView
        val createdDatetv: TextView
        val deletedDatetv: TextView
        val expirationDatetv: TextView


        init {
            externalIdtv = itemView.findViewById(R.id.getApisExternalId)
            administrativeUserLogintv = itemView.findViewById(R.id.getApisAdministrativeUserLogin)
            customerNumbertv = itemView.findViewById(R.id.getApisCustomerNumber)
            descriptiontv = itemView.findViewById(R.id.getApisDescription)
            enabledtv = itemView.findViewById(R.id.getApisEnabled)
            disableReasontv = itemView.findViewById(R.id.getApisDisabledReason)
            createdDatetv = itemView.findViewById(R.id.getApisCreatedDate)
            deletedDatetv = itemView.findViewById(R.id.getApisDeletedDate)
            expirationDatetv = itemView.findViewById(R.id.getApisExpirationDate)
        }
        fun bindView(item: ApiKeyLite) {

            val externalId = "ExternalId: "+ item.ExternalId
            val administrativeUserLogin = "AdministrativeUserLogin: " + item.AdministrativeUserLogin
            val customerNumber = "CustomerNumber: "+ item.CustomerNumber
            val description = "Description: " + item.Description
            val enabled = "Enabled :" + item.Enabled.toString()
            val disableReason = "DisableReason: " + item.DisableReason
            val deletedDate = "DeletedDate: " + item.DeletedDate.toString()
            val createdDate = "CreatedDate: " + item.CreatedDate.toString()
            val expirationDate = "ExpirationDate: " + item.ExpirationDate.toString()


            externalIdtv.text = externalId
            administrativeUserLogintv.text = administrativeUserLogin
            customerNumbertv.text = customerNumber
            descriptiontv.text = description
            enabledtv.text = enabled
            disableReasontv.text =  disableReason
            deletedDatetv.text = deletedDate
            createdDatetv.text = createdDate
            expirationDatetv.text = expirationDate
        }
    }


}