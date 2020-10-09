package com.eargel.karmag5.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.eargel.karmag5.R
import com.eargel.karmag5.viewmodel.HacerViewModel
import kotlinx.android.synthetic.main.hacer_fragment.*
import kotlinx.android.synthetic.main.signup_fragment.*
import kotlinx.android.synthetic.main.solicitar_fragment.*

class HacerFragment : Fragment() {

    companion object {
        fun newInstance() = HacerFragment()
    }

    private lateinit var viewModel: HacerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hacer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HacerViewModel::class.java)
        // TODO: Use the ViewModel

        imageButtonBackPerfilFronHacer.setOnClickListener{
            findNavController().navigate(R.id.action_hacerFragment_to_perfilFragment)
        }

    }

}