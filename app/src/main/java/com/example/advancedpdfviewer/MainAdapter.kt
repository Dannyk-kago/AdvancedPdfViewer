package com.example.advancedpdfviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter():RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    class MainViewHolder(private val itemView:View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
       return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        TODO()
    }
}