package com.eargel.karmag5.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.eargel.karmag5.R
import com.eargel.karmag5.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.signup_fragment.*

class SignupFragment : Fragment() {

    companion object {
        fun newInstance() = SignupFragment()
    }

    private lateinit var viewModel: SignupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)
        // TODO: Use the ViewModel


        imageButtonBackLogIng.setOnClickListener{
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        buttonFinishUp.setOnClickListener{
            findNavController().navigate(R.id.action_signupFragment_to_perfilFragment)
        }
    }

}