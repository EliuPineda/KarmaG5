package com.eargel.karmag5.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eargel.karmag5.viewmodel.PerfilViewModel
import com.eargel.karmag5.R
import com.eargel.karmag5.model.Favor
import kotlinx.android.synthetic.main.perfil_fragment.*

class PerfilFragment : Fragment() {

    companion object {
        fun newInstance() = PerfilFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.perfil_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel: PerfilViewModel by viewModels()
        var authenticatedUID = ""

        viewModel.authenticatedUserLiveData().observe(viewLifecycleOwner) { authenticatedUser ->
            if (authenticatedUser == null)
                findNavController().navigate(R.id.action_perfilFragment_to_loginFragment)
            else {
                authenticatedUID = authenticatedUser.uid
                textKarma.text = authenticatedUser.karma.toString()
                viewModel.buscarFavorEnProceso(authenticatedUser)
            }
        }

        viewModel.favorEnProcesoLiveData().observe(viewLifecycleOwner) { favorEnProceso ->
            if (favorEnProceso == null) {
                textViewCategoria.text = ""
                textViewEstado.text = ""
                textViewLugar.text = ""
                textViewDetalle.text = ""
                buttonGoHacer.isEnabled = true
                buttonGoSolicitar.isEnabled = true
            } else {
                textViewCategoria.text = favorEnProceso.categoria
                textViewEstado.text = favorEnProceso.estado
                textViewLugar.text = favorEnProceso.lugar
                textViewDetalle.text = favorEnProceso.detalle
                buttonGoHacer.isEnabled = false
                buttonGoSolicitar.isEnabled = false
            }
        }

        viewModel.favoresHechosLiveData().observe(viewLifecycleOwner) { favoresHechos ->
            fun describe(favor: Favor): String {
                if (favor.user!!.uid == authenticatedUID)
                    return "Pediste: ${favor.categoria} (-2)"
                else
                    return "Hiciste: ${favor.categoria} (+1)"
            }

            if (favoresHechos != null) {
                textFavor0.text = describe(favoresHechos.get(0))
                if (favoresHechos.size <= 2) {
                    textFavor1.text = describe(favoresHechos.get(1))
                    if (favoresHechos.size <= 3) {
                        textFavor2.text = describe(favoresHechos.get(2))
                    }
                }
            }

        }

        imageButtonLogOut.setOnClickListener {
            viewModel.signOut()
        }

        buttonGoHacer.setOnClickListener {
            findNavController().navigate(R.id.action_perfilFragment_to_hacerFragment)
        }

        buttonGoSolicitar.setOnClickListener {
            findNavController().navigate(R.id.action_perfilFragment_to_solicitarFragment)
        }

        imageButtonChat.setOnClickListener{
            findNavController().navigate(R.id.action_perfilFragment_to_chatFragment)
        }

    }

}