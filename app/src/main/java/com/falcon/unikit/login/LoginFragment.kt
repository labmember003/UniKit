package com.falcon.unikit.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.falcon.unikit.R
import com.falcon.unikit.databinding.FragmentLoginBinding
import com.falcon.unikit.model.UserRequest
import com.falcon.unikit.utils.NetworkResult
import com.falcon.unikit.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {
    //    TODO("ANIMATION AND BACK BEHAVIOUR")
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginPanel.setOnClickListener {
            val validateResult = validateUserInput()
            if (validateResult.first) {
                binding.loginButton.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
                binding.animationView.setAnimation("loading-dots.json")
                binding.animationView.playAnimation()
                authViewModel.loginUser(getUserRequest())
            } else {
                binding.errorTextview.text = validateResult.second
                binding.errorTextview.visibility = View.VISIBLE
            }
        }
        binding.registerNowTxt.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        bindObservers()
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val userRequest = getUserRequest()
        return authViewModel.validateCredentials(userRequest.username, userRequest.email, userRequest.password, true)
    }

    private fun getUserRequest(): UserRequest {
        return UserRequest(binding.email.text.toString(), binding.password.text.toString(),"")
    }

    private fun bindObservers() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    tokenManager.saveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_resources)
                }
                is NetworkResult.Error -> {
                    binding.errorTextview.text = it.message
                    binding.errorTextview.visibility = View.VISIBLE
                    binding.animationView.visibility = View.GONE
                    binding.loginButton.visibility = View.VISIBLE
                }
                is NetworkResult.Loading -> {
                    binding.loginButton.visibility = View.GONE
                    binding.animationView.visibility = View.VISIBLE
                    binding.animationView.setAnimation("loading-dots.json")
                    binding.animationView.playAnimation()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}