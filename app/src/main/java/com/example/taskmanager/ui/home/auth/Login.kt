package com.example.taskmanager.ui.home.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentLoginBinding
import com.example.taskmanager.databinding.FragmentSignupBinding
import com.example.taskmanager.domain.model.SignInStatus
import com.example.taskmanager.domain.model.SignUpStatus
import com.example.taskmanager.domain.model.User
import com.example.taskmanager.ui.home.viewmodels.AuthViewModel
import com.example.taskmanager.utils.safeNavigate
import com.example.taskmanager.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class Login : Fragment(R.layout.fragment_login) {

    private lateinit var binding : FragmentLoginBinding
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        binding.lifecycleOwner = this

        initViews()
        attachObservers()
    }

    private fun initViews() {
        binding.signupButton.setOnClickListener {
            safeNavigate(R.id.action_login_to_signup)
        }

        binding.loginButton.setOnClickListener { startSignInProcess() }
    }


    private fun attachObservers() {
        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                authViewModel.signInStatus.collect {
                    when(it) {
                        SignInStatus.SIGN_IN_STARTED -> {
                            binding.loginButton.isEnabled = false
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        SignInStatus.SIGN_IN_SUCCESS -> {
                            findNavController().navigateUp()
                            showToast("Login successful")
                        }
                        SignInStatus.SIGN_IN_FAILURE -> {
                            showToast("Try again")
                            binding.loginButton.isEnabled = true
                            binding.progressBar.visibility = View.GONE
                        }
                        null -> Unit
                    }
                }

            }
        }
    }

    private fun getUser(): User? {
        val username = binding.username.text?.toString() ?: return null
        val password = binding.password.text?.toString() ?: return null

        return User(username, "", password)
    }

    private fun startSignInProcess() {
        val user = getUser() ?: return
        authViewModel.login(user)
    }

}