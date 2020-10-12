package com.eargel.karmag5.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.eargel.karmag5.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.login_fragment.*
import com.eargel.karmag5.R


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    val SIGN_IN_CODE = 23
    val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("347292527764-6vljnsr6oo655pd031ilh00mq1iibb56.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        buttonSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, SIGN_IN_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SIGN_IN_CODE) {
            try {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                viewModel.signInWithGoogle(task.result)

                viewModel.authenticatedUserLiveData?.observe(viewLifecycleOwner) { authenticatedUser ->
                    findNavController().navigate(R.id.action_loginFragment_to_perfilFragment)
                }
            } catch (e: ApiException) {
                Log.e("LOGIN", "API Exception: $e")
                Toast.makeText(activity, "API Exception: $e", Toast.LENGTH_LONG).show()
            }
        }
    }
}


