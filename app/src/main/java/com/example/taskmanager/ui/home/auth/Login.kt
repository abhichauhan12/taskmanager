package com.example.taskmanager.ui.home.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentLoginBinding
import com.example.taskmanager.utils.safeNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : Fragment(R.layout.fragment_login) {

    private lateinit var binding : FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        binding.lifecycleOwner = this

        initViews()
    }

    private fun initViews() {
        binding.signupButton.setOnClickListener {
            safeNavigate(R.id.action_login_to_signup)
        }
    }

}