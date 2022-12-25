package com.falcon.unikit.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PapersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "paper"
    }
    val text: LiveData<String> = _text
}