package com.falcon.unikit.login

import android.R
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.falcon.unikit.databinding.FragmentUserDataBinding
import com.falcon.unikit.network.RegionApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*


class UserDataFragment : Fragment() {

    private var _binding: FragmentUserDataBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserDataBinding.inflate(inflater, container, false)


        val countryCodes = Locale.getISOCountries()

        val countryNames = countryCodes.map { Locale("", it).displayCountry }
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, countryNames)
        binding.autoCompleteTextView.setAdapter(adapter)
//
//        val geocoder = Geocoder(requireContext(), Locale.getDefault())
//        val addresses = geocoder.getFromLocationName("India", 1)
//        val cityNames = addresses?.map { it.locality } as kotlin.collections.List
//        val cityNamess = addresses?.map { it.locality }
////        Toast.makeText(requireContext(), cityNamess?.get(0), Toast.LENGTH_SHORT).show()
//        val spinnerCity = binding.spinnerCity
//        val adapter2 = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, cityNames)
//        spinnerCity.adapter = adapter2
//        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addresses = geocoder.getFromLocationName("Australia", 100)
        val cityNames = addresses?.mapNotNull { it.locality }
//        Toast.makeText(requireContext(), cityNames?.size.toString(), Toast.LENGTH_SHORT).show()



        var viewModelJob = Job()
        val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )
        coroutineScope.launch {
            try {
                Toast.makeText(requireContext(), "IN", Toast.LENGTH_SHORT).show()
                val countryList = RegionApi.apiService.getCountriesAsync().await().geonames
                val a = countryList.size
                Toast.makeText(requireContext(), a, Toast.LENGTH_SHORT).show()
                Toast.makeText(requireContext(), countryList.size, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("tatti", e.stackTraceToString())
                Toast.makeText(requireContext(), "OUT", Toast.LENGTH_SHORT).show()
            }
        }
//        val countryList  = RegionApi.apiService.getCountriesAsync().geonames
//        Toast.makeText(requireContext(), countryList?.size ?: 4, Toast.LENGTH_SHORT).show()

        return binding.root



//        https://api.geonames.org

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}