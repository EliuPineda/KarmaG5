package com.eargel.karmag5.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.eargel.karmag5.viewmodel.PerfilViewModel
import com.eargel.karmag5.R
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.perfil_fragment.*
import kotlinx.android.synthetic.main.signup_fragment.*

class PerfilFragment : Fragment() {

    companion object {
        fun newInstance() = PerfilFragment()
    }

    private lateinit var viewModel: PerfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.perfil_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PerfilViewModel::class.java)
        // TODO: Use the ViewModel


        imageButtonLogOut.setOnClickListener{
            findNavController().navigate(R.id.action_perfilFragment_to_loginFragment)
        }

        buttonGoHacer.setOnClickListener{
            findNavController().navigate(R.id.action_perfilFragment_to_hacerFragment)
        }

        buttonGoSolicitar.setOnClickListener{
            findNavController().navigate(R.id.action_perfilFragment_to_solicitarFragment)
        }

        imageButtonChat.setOnClickListener{
            findNavController().navigate(R.id.action_perfilFragment_to_chatFragment)
        }

    }

}