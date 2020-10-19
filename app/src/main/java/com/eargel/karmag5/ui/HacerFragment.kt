package com.eargel.karmag5.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eargel.karmag5.R
import com.eargel.karmag5.model.Favor
import com.eargel.karmag5.viewmodel.HacerViewModel
import kotlinx.android.synthetic.main.hacer_fragment.*

class HacerFragment : Fragment() {

    companion object {
        fun newInstance() = HacerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hacer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel: HacerViewModel by viewModels()

        val categorias = mapOf(
            R.id.radioDomicilioF to "Buscar Domicilio en Puerta 7",
            R.id.radioEspecialF to "Favor Especial",
            R.id.radioFotocopiasF to "Sacar Fotocopias",
            R.id.radioKM5F to "Comprar en KM5"
        )

        radioGroup.clearCheck()
        viewFavores.layoutManager = LinearLayoutManager(requireContext())
        viewFavores.adapter = FavoresAdapter(mutableListOf()) { favor ->
            viewModel.asignarFavor(favor)
            findNavController().navigateUp()
        }

        var favoresDisponibles = listOf<Favor>()

        fun filtrarYMostrarFavores(checkedId: Int) {
            with(viewFavores.adapter as FavoresAdapter) {
                favores.clear()
                if (checkedId < 0)
                    favores.addAll(favoresDisponibles)
                else
                    favores.addAll(favoresDisponibles.filter { f -> f.categoria == categorias[checkedId] })
                notifyDataSetChanged()
            }
        }

        radioGroup.setOnCheckedChangeListener { radioGroup: RadioGroup, checkedId: Int ->
            filtrarYMostrarFavores(checkedId)
        }

        viewModel.favoresDisponiblesLiveData().observe(viewLifecycleOwner) { favores ->
            favoresDisponibles = favores
            filtrarYMostrarFavores(radioGroup.checkedRadioButtonId)
        }

        buttonSinFiltro.setOnClickListener {
            radioGroup.clearCheck()
        }

        imageButtonBackPerfilFromHacer.setOnClickListener {
            findNavController().navigateUp()
        }

    }

}