package com.example.taskmanager.ui.add

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentColorBottomSheetBinding
import com.example.taskmanager.ui.HomeActivity
import com.example.taskmanager.ui.UtilsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ColorBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentColorBottomSheetBinding

    private val utilsViewModel by lazy {
        ViewModelProvider(
            requireActivity() as HomeActivity,
            UtilsViewModel.Factory()
        )[UtilsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentColorBottomSheetBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.color1.setOnClickListener { onColorSelected(it) }
        binding.color2.setOnClickListener { onColorSelected(it) }
        binding.color3.setOnClickListener { onColorSelected(it) }
        binding.color4.setOnClickListener { onColorSelected(it) }
        binding.color5.setOnClickListener { onColorSelected(it) }
        binding.color6.setOnClickListener { onColorSelected(it) }
        binding.color7.setOnClickListener { onColorSelected(it) }
        binding.color8.setOnClickListener { onColorSelected(it) }
        binding.color9.setOnClickListener { onColorSelected(it) }
        binding.color10.setOnClickListener { onColorSelected(it) }
        binding.color11.setOnClickListener { onColorSelected(it) }
        binding.color12.setOnClickListener { onColorSelected(it) }
        binding.color13.setOnClickListener { onColorSelected(it) }
        binding.color14.setOnClickListener { onColorSelected(it) }

    }

    fun onColorSelected(v: View) {
        val color = when (v.id) {
            R.id.color1 -> Color.parseColor("#00251a")
            R.id.color2 -> Color.parseColor("#3E2723")
            R.id.color3 -> Color.parseColor("#01579B")
            R.id.color4 -> Color.parseColor("#880E4F")
            R.id.color5 -> Color.parseColor("#B71C1C")
            R.id.color6 -> Color.parseColor("#212121")
            R.id.color7 -> Color.parseColor("#000000")
            R.id.color8 -> Color.parseColor("#5C0000")
            R.id.color9 -> Color.parseColor("#372F01")
            R.id.color10 -> Color.parseColor("#012039")
            R.id.color11 -> Color.parseColor("#D50B77")
            R.id.color12 -> Color.parseColor("#168A1B")
            R.id.color13 -> Color.parseColor("#784343")
            else  -> Color.parseColor("#2F505A")
        }
        utilsViewModel.updateSelectedColor(color)
        dismiss()

    }


}