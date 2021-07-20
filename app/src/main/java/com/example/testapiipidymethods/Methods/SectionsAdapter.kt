package com.example.testapiipidymethods.Methods

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapiipidymethods.R
import java.util.ArrayList

class SectionsAdapter (private  val sections: ArrayList<Section>,
                       private  val listener: OnItemClickListener
                       ): RecyclerView.Adapter<SectionsAdapter.SectionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        return SectionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.section_item, parent, false))
    }

    override fun getItemCount(): Int {
        return sections.size
    }
    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val item = sections.get(position)
        holder.bindView(item)
        holder.itemView.setOnClickListener {  }
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
        val textView: TextView
        init {
            textView = itemView.findViewById(R.id.nameSection)
        }
        fun bindView(item: Section) {
            textView.text = item.name
        }

        override fun onClick(view: View?) {

            val position: Int = adapterPosition

            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }

        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}