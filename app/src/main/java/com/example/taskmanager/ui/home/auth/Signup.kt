package com.example.taskmanager.ui.home.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.amplifyframework.core.Amplify
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentSignupBinding
import com.example.taskmanager.domain.model.AuthStatus
import com.example.taskmanager.domain.model.User
import com.example.taskmanager.ui.home.viewmodels.AuthViewModel
import com.example.taskmanager.utils.safeNavigate
import com.example.taskmanager.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class Signup : Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignupBinding.bind(view)
        binding.lifecycleOwner = this

        initViews()
        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                authViewModel.authStatus.collect {
                    when (it.first) {
                        AuthStatus.LOADING -> {
                            showToast("Sign up in progress")
                            binding.signupButton.isEnabled = false
                        }
                        AuthStatus.SUCCESS -> {
                            showToast("OTP sent on your mail", true)
                            binding.signupButton.isEnabled = true
                        }
                        AuthStatus.FAILURE -> {
                            showToast(it.second)
                            binding.signupButton.isEnabled = false
                        }
                        null -> Unit
                    }
                }

            }
        }
    }

    private fun verifyOTP() {
        val user = getUser() ?: return

        Amplify.Auth.confirmSignUp(
            user.username, binding.otp.text?.toString() ?: "",
            { result ->
                if (result.isSignUpComplete) {
                    lifecycleScope.launchWhenStarted {
                        withContext(Dispatchers.Main) {
                            showToast("Sign up successful")
                            findNavController().navigateUp()
                        }
                    }
                } else {

                    lifecycleScope.launchWhenStarted {
                        withContext(Dispatchers.Main) {
                            showToast("Sign up failed")
                        }
                    }
                }
            },
            {
                lifecycleScope.launchWhenStarted {
                    withContext(Dispatchers.Main) {
                        showToast(it.message ?: "Some error occurred")
                    }
                }
            }

        )
    }

    private fun initViews() {
        binding.loginButton.setOnClickListener {
            safeNavigate(R.id.action_signup_to_login)
        }

        binding.signupButton.setOnClickListener {
            if (authViewModel.authStatus.value.first == AuthStatus.SUCCESS)
                verifyOTP()
            else
                startSignUpProcess()
        }
    }

    private fun getUser(): User? {
        val username = binding.username.text?.toString() ?: return null
        val email = binding.email.text?.toString() ?: return null
        val password = binding.password.text?.toString() ?: return null

        return User(username, email, password)
    }

    private fun startSignUpProcess() {
        val user = getUser() ?: return

        authViewModel.signUp(
            user,
            onSuccess = { authViewModel.updateAuthStatus(AuthStatus.SUCCESS, "") },
            onFailure = { authViewModel.updateAuthStatus(AuthStatus.FAILURE, it) })
    }

}