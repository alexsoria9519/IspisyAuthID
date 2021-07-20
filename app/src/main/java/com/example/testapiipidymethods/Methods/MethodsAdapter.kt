package com.example.testapiipidymethods.Methods

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapiipidymethods.R
import java.util.ArrayList

class MethodsAdapter (private  var methods: ArrayList<Method>): RecyclerView.Adapter<MethodsAdapter.MethodsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodsViewHolder {
        return MethodsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.method_item, parent, false))
    }

    override fun getItemCount(): Int {
        return methods.size
    }
    override fun onBindViewHolder(holder: MethodsViewHolder, position: Int) {
        val item = methods.get(position)
        holder.bindView(item)
    }

    class MethodsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView: TextView
        init {
            textView = itemView.findViewById(R.id.nameMethod)
        }
        fun bindView(item: Method) {
            textView.text = item.name
        }
    }

}