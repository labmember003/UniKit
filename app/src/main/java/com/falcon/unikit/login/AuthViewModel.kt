package com.falcon.unikit.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falcon.unikit.model.UserRequest
import com.falcon.unikit.model.UserResponse
import com.falcon.unikit.repository.UserRepository
import com.falcon.unikit.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (private val userRepository: UserRepository): ViewModel() {

    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
    get() = userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }
    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun validateCredentials(userName: String, emailAddress: String, password: String, isLogin: Boolean): Pair<Boolean, String> {
        var result = Pair(true, "")
        if ((!isLogin && userName.isEmpty()) || emailAddress.isEmpty() || password.isEmpty()) {
            result =  Pair(false, "Please Enter All The Fields")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            result =  Pair(false, "Please Enter Valid E-Mail")
        } else if (password.length <= 5) {
            result =  Pair(false, "Password Length Should Be Greater Than 5")
        }
        return result
    }
}