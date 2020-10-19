package com.eargel.karmag5.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eargel.karmag5.viewmodel.PerfilViewModel
import com.eargel.karmag5.R
import com.eargel.karmag5.model.Favor
import com.eargel.karmag5.model.User
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
        lateinit var user: User

        viewModel.authenticatedUserLiveData().observe(viewLifecycleOwner) { authenticatedUser ->
            if (authenticatedUser == null)
                findNavController().navigate(R.id.action_perfilFragment_to_loginFragment)
            else {
                user = authenticatedUser
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
                imageButtonChat.isEnabled = false
                buttonCompletado.isEnabled = false
                buttonCancelar.isEnabled = false
            } else {
                textViewCategoria.text = favorEnProceso.categoria
                textViewEstado.text = favorEnProceso.estado
                textViewLugar.text = favorEnProceso.lugar
                textViewDetalle.text = favorEnProceso.detalle
                buttonGoHacer.isEnabled = false
                buttonGoSolicitar.isEnabled = false
                imageButtonChat.isEnabled = (favorEnProceso.userAsignado != null)
                buttonCompletado.isEnabled = (favorEnProceso.userAsignado != null)
                buttonCancelar.isEnabled =
                    (favorEnProceso.userAsignado == null || favorEnProceso.userAsignado!!.uid == user.uid)
            }
        }

        viewModel.favoresHechosLiveData().observe(viewLifecycleOwner) { favoresHechos ->
            fun describe(favor: Favor): String {
                if (favor.user?.uid == user.uid)
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
            if (user.karma < 2)
                Toast.makeText(
                    activity,
                    "No tienes suficiente Karma para pedir favores",
                    Toast.LENGTH_LONG
                ).show()
            else
                findNavController().navigate(R.id.action_perfilFragment_to_solicitarFragment)
        }

        imageButtonChat.setOnClickListener {
            findNavController().navigate(R.id.action_perfilFragment_to_chatFragment)
        }

        buttonCancelar.setOnClickListener {
            viewModel.cancelarFavorEnProceso(user)
        }

        buttonCompletado.setOnClickListener {
            viewModel.confirmarFavorEnProceso(user)
        }
    }

}
