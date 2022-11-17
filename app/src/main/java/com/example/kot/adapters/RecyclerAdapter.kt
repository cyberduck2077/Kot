package com.example.kot.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kot.R
import com.example.kot.model.user.UserDataModel

class RecyclerAdapter(private val list: List<UserDataModel>) :
    RecyclerView.Adapter<RecyclerAdapter.MViewHolder>() {

    class MViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text1: TextView = itemView.findViewById(R.id.cardText1)
        val text2: TextView = itemView.findViewById(R.id.cardText2)
        val text3: TextView = itemView.findViewById(R.id.cardText3)
        val text4: TextView = itemView.findViewById(R.id.cardText4)
        val text5: TextView = itemView.findViewById(R.id.cardText5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_for_user, parent, false)
        return MViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.text1.text = "name: ${list[position].name}"
        holder.text2.text = "username: ${list[position].username}"
        holder.text3.text = "email: ${list[position].email}"
        holder.text4.text = "company name: ${list[position].company.name}"
        holder.text5.text = "city: ${list[position].address.city}"
    }

    override fun getItemCount(): Int {
        return list.size
    }
}