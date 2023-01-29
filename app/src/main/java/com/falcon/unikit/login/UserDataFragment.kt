package com.falcon.unikit.login

import RegionApi
import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.falcon.unikit.databinding.FragmentUserDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

var countryCode = ""

class UserDataFragment : Fragment() {

    private var _binding: FragmentUserDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserDataBinding.inflate(inflater, container, false)
        val viewModelJob = Job()
        val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
        coroutineScope.launch {
            try {
                Toast.makeText(requireContext(), "IN", Toast.LENGTH_SHORT).show()
                val countryLists = RegionApi.apiService.getCountriesAsync().await()
                val countryList =  countryLists.map { it.countryName } as MutableList<String>
                val adapter2 = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, countryList)
                binding.countriesAutoCompleteTextView.setAdapter(adapter2)
                var statesList = RegionApi.apiService.getStates("IN").await().map { it.cityName } as MutableList<String>
                binding.countriesAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
                    binding.statesAutoCompleteTextView.setText("City")
                    countryCode = countryLists[position].countryCode
                    coroutineScope.launch {
                        val stateList = RegionApi.apiService.getStates(countryCode).await().map { it.cityName } as MutableList<String>
                        binding.statesAutoCompleteTextView.visibility = View.VISIBLE
                        binding.stateTextInputLayout.visibility = View.VISIBLE
                        if (stateList.isEmpty()) {
                            binding.statesAutoCompleteTextView.visibility = View.INVISIBLE
                            binding.stateTextInputLayout.visibility = View.INVISIBLE
                        }
                        val adapter3 = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, stateList)
                        binding.statesAutoCompleteTextView.setAdapter(adapter3)
                    }
                }
            } catch (e: Exception) {
                Log.e("tatti", e.stackTraceToString())
                Toast.makeText(requireContext(), "OUT", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





