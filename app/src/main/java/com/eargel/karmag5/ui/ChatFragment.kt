package com.eargel.karmag5.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eargel.karmag5.viewmodel.ChatViewModel
import com.eargel.karmag5.R
import kotlinx.android.synthetic.main.chat_fragment.*

class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel: ChatViewModel by viewModels()

        viewMensajes.layoutManager = LinearLayoutManager(requireContext())
        viewMensajes.adapter = MensajesAdapter(mutableListOf())

        viewModel.favorEnProcesoLiveData().observe(viewLifecycleOwner) { favor ->
            textFavor.text = favor.categoria
            with(viewMensajes.adapter as MensajesAdapter) {
                mensajes.clear()
                mensajes.addAll(favor.mensajes.values.sortedBy { mensaje -> mensaje.hora })
                notifyDataSetChanged()
            }
            viewMensajes.scrollToPosition(favor.mensajes.size - 1)
        }

        imageButtonEnviar.setOnClickListener {
            val mensaje = textNuevoMensaje.text.toString()
            if (mensaje != "") {
                viewModel.enviarMensaje(mensaje)
                textNuevoMensaje.text = null
            }
        }

        imageButtonBackPerfilFromChat.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}