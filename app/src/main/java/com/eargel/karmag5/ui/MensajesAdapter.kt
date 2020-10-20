package com.eargel.karmag5.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eargel.karmag5.R
import com.eargel.karmag5.model.Mensaje
import kotlinx.android.synthetic.main.layout_list_item_mensaje.view.*

class MensajesAdapter(var mensajes: MutableList<Mensaje>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MensajesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_item_mensaje, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MensajesViewHolder -> {
                holder.bind(mensajes.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return mensajes.size
    }

    class MensajesViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(mensaje: Mensaje) {
            with(itemView) {
                textNombreUser.text = mensaje.user?.nombre
                textMensaje.text = mensaje.texto
            }
        }
    }
}