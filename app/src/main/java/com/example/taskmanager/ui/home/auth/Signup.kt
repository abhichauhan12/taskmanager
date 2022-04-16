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
import com.example.taskmanager.databinding.FragmentSignupBinding
import com.example.taskmanager.domain.model.SignUpStatus
import com.example.taskmanager.domain.model.User
import com.example.taskmanager.ui.home.viewmodels.AuthViewModel
import com.example.taskmanager.utils.safeNavigate
import com.example.taskmanager.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class Signup : Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignupBinding.bind(view)
        binding.lifecycleOwner = this

        initViews()
        attachObservers()
    }

    private fun attachObservers() {
        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                authViewModel.signUpStatus.collect {
                    when(it) {
                        SignUpStatus.SIGN_UP_STARTED -> {
                            binding.signupButton.isEnabled = false
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        SignUpStatus.SIGN_UP_SUCCESS -> {
                            binding.signupButton.isEnabled = true
                            binding.progressBar.visibility = View.GONE
                            binding.signupButton.text = getString(R.string.verify)
                            binding.username.isEnabled = false
                            binding.email.isEnabled = false
                            binding.password.isEnabled = false
                            binding.otp.visibility = View.VISIBLE
                        }

                        SignUpStatus.SIGN_UP_FAILURE -> {
                            binding.signupButton.isEnabled = true
                            binding.progressBar.visibility = View.GONE
                        }

                        SignUpStatus.VERIFY_STARTED -> {
                            binding.signupButton.isEnabled = false
                            binding.progressBar.visibility = View.VISIBLE
                            binding.otp.isEnabled = false
                        }

                        SignUpStatus.VERIFY_SUCCESS -> {
                            binding.signupButton.isEnabled = false
                            binding.progressBar.visibility = View.VISIBLE
                            binding.otp.isEnabled = false
                            findNavController().navigateUp()
                            showToast("Sign up success")
                        }

                        SignUpStatus.VERIFY_FAILURE -> {
                            binding.signupButton.isEnabled = true
                            binding.progressBar.visibility = View.GONE
                            binding.otp.isEnabled = true
                        }

                        null -> Unit
                    }
                }

            }
        }
    }

    private fun verifyOTP() {
        val user = getUser() ?: return
        val otp = binding.otp.text?.toString() ?: return

        authViewModel.verifyOtp(user, otp)
    }

    private fun initViews() {
        binding.loginButton.setOnClickListener {
            safeNavigate(R.id.action_signup_to_login)
        }

        binding.signupButton.setOnClickListener {
            if (authViewModel.signUpStatus.value == SignUpStatus.SIGN_UP_SUCCESS) verifyOTP()
            else startSignUpProcess()
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
        authViewModel.signUp(user)
    }

}