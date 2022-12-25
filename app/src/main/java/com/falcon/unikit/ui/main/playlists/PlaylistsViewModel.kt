package com.falcon.unikit.ui.main.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlaylistsViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Playlists"
    }
    val text: LiveData<String> = _text
}