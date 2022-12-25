package com.falcon.unikit.ui.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.falcon.unikit.databinding.FragmentPrintBinding

class PrintFragment : Fragment() {
    private var _binding: FragmentPrintBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrintBinding.inflate(inflater, container, false)
        val url = "https://www.ipuranklist.com/student"
        val url1 = "http://myproxy.com?t=https://www.ipuranklist.com/student"
        binding.resultWebView.settings.javaScriptEnabled = true
        binding.resultWebView.settings.userAgentString = "Android"
//        binding.resultWebView.goBack()

        binding.resultWebView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.imagePendingAnimation.visibility = View.GONE
                binding.resultWebView.visibility = View.VISIBLE
            }
        }
        binding.resultWebView.loadUrl(url)

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Fragment back pressed invoked")
                    if (binding.resultWebView.canGoBack()) {
                        binding.resultWebView.goBack()
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            }
            )
        return binding.root
    }
}