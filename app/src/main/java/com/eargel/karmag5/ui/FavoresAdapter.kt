package com.eargel.karmag5.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eargel.karmag5.R
import com.eargel.karmag5.model.Favor
import kotlinx.android.synthetic.main.layout_list_item_favor.view.*

class FavoresAdapter(var favores: MutableList<Favor>, val favorOnClickListener: (Favor) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavoresViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_item_favor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FavoresViewHolder -> {
                holder.bind(favores.get(position), favorOnClickListener)
            }
        }
    }

    override fun getItemCount(): Int {
        return favores.size
    }

    class FavoresViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(favor: Favor, favorOnClickListener: (Favor) -> Unit) {
            with(itemView) {
                textCategoria.text = favor.categoria
                textLugar.text = "Lugar de entrega: ${favor.lugar}"
                textUser.text = "Pedido por: ${favor.user?.nombre}"
                setOnClickListener { favorOnClickListener(favor) }
            }
        }
    }
}