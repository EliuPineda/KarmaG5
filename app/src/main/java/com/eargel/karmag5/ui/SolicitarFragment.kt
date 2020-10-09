package com.eargel.karmag5.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.eargel.karmag5.R
import com.eargel.karmag5.viewmodel.SolicitarViewModel
import kotlinx.android.synthetic.main.solicitar_fragment.*

class SolicitarFragment : Fragment() {

    companion object {
        fun newInstance() = SolicitarFragment()
    }

    private lateinit var viewModel: SolicitarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.solicitar_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SolicitarViewModel::class.java)
        // TODO: Use the ViewModel

        imageButtonBackPerfilFronSolicitar.setOnClickListener{
            findNavController().navigate(R.id.action_solicitarFragment_to_perfilFragment)
        }

        buttonDeseoSolicitar.setOnClickListener{
            findNavController().navigate(R.id.action_solicitarFragment_to_perfilFragment)
        }
    }

}