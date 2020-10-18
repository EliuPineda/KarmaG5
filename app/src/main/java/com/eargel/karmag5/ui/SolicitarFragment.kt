package com.eargel.karmag5.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eargel.karmag5.R
import com.eargel.karmag5.viewmodel.SolicitarViewModel
import kotlinx.android.synthetic.main.solicitar_fragment.*

class SolicitarFragment : Fragment() {

    companion object {
        fun newInstance() = SolicitarFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.solicitar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel: SolicitarViewModel by viewModels()

        viewModel.authenticatedUserLiveData().observe(viewLifecycleOwner) { authenticatedUser ->
            if (authenticatedUser == null)
                findNavController().navigateUp()
            else if (authenticatedUser.karma < 2) {
                Toast.makeText(
                    activity,
                    "No tienes suficiente Karma para pedir favores.",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigateUp()
            }
        }

        val categorias = mapOf(
            R.id.radioDomicilio to "Buscar Domicilio en Puerta 7",
            R.id.radioEspecial to "Favor Especial",
            R.id.radioFotocopias to "Sacar Fotocopias",
            R.id.radioKM5 to "Comprar en KM5"
        )

        imageButtonBackPerfilFromSolicitar.setOnClickListener {
            findNavController().navigateUp()
        }

        buttonDeseoSolicitar.setOnClickListener {
            val lugar = textLugar.text.toString()
            val detalle = textDetalle.text.toString()
            val categoria = categorias[radioGroup2.checkedRadioButtonId]
            if (lugar != "" && detalle != "" && categoria != null) {
                viewModel.solicitarFavor(lugar, detalle, categoria)
                findNavController().navigateUp()
            }
        }
    }

}