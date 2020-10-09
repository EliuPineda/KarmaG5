package com.eargel.karmag5.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.eargel.karmag5.viewmodel.ChatViewModel
import com.eargel.karmag5.R
import kotlinx.android.synthetic.main.chat_fragment.*
import kotlinx.android.synthetic.main.signup_fragment.*

class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        // TODO: Use the ViewModel

        imageButtonBackPerfilFronChat.setOnClickListener{
            findNavController().navigate(R.id.action_chatFragment_to_perfilFragment)
        }
    }

}