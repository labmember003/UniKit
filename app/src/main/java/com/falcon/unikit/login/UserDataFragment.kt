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
import com.falcon.unikit.network.Country
//import com.falcon.unikit.network.RegionApi
import com.falcon.unikit.network.StateResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


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
//        val geocoder = Geocoder(requireContext(), Locale.getDefault())
//        val addresses = geocoder.getFromLocationName("Australia", 100)
//        val cityNames = addresses?.mapNotNull { it.locality }
//        Toast.makeText(requireContext(), cityNames?.size.toString(), Toast.LENGTH_SHORT).show()





        var viewModelJob = Job()
        val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )
        coroutineScope.launch {
            try {
                Toast.makeText(requireContext(), "IN", Toast.LENGTH_SHORT).show()
                var countryList = RegionApi.apiService.getCountriesAsync().await().map { it.countryName } as MutableList<String>
                val adapter2 = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, countryList)
                binding.countriesAutoCompleteTextView.setAdapter(adapter2)
//                val stateListOfCountry = RegionApi.apiService.getStates().await()[1].cityName
//                binding.countriesAutoCompleteTextView.setOnItemClickListener()
//                countryList[0]
                var statesList = RegionApi.apiService.getStates().await().map { it.cityName } as MutableList<String>
                binding.countriesAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
//                    Toast.makeText(requireContext(), position, Toast.LENGTH_SHORT).show()
//                    val stateListOfCountry = RegionApi.apiService.getStates().await()[position].cityName
//                    getStates().await().map { it.cityName } as MutableList<String>
//                    val stateListOfCountry = RegionApi.apiService.getStates().await()
//                    [position].cityName
//                        setStateList(stateListOfCountry, position)
                }
//                fun setStateList(stateListOfCountry: String, position: Int) {

//                }
            } catch (e: Exception) {
                Log.e("tatti", e.stackTraceToString())
                Toast.makeText(requireContext(), "OUT", Toast.LENGTH_SHORT).show()
            }
        }

        var viewModelJobb = Job()
        val coroutineScopee = CoroutineScope(viewModelJobb + Dispatchers.Main )
        coroutineScopee.launch {
            try {
                Toast.makeText(requireContext(), "INn", Toast.LENGTH_SHORT).show()
                val stateList = RegionApi.apiService.getStates().await().map { it.cityName } as MutableList<String>
                val adapter3 = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, stateList)
                binding.statesAutoCompleteTextView.setAdapter(adapter3)
//                Toast.makeText(requireContext(), stateList[5], Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("tatti", e.stackTraceToString())
                Toast.makeText(requireContext(), "OUT", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root



//        https://api.geonames.org

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
/*
val stateListOfCountry = RegionApi.apiService.getStates().await()[position].cityName
 */















private const val BASE_URL = "https://api.countrystatecity.in/v1/"

private val moshi = Moshi.Builder().build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(OkHttpClient().newBuilder().addInterceptor { chain: Interceptor.Chain ->
        val original: Request = chain.request()
        val authorized: Request = original.newBuilder()
            .addHeader("X-CSCAPI-KEY", "R3FsUnlwVVpBSUE5RDVkTDRiZGlUSUVFT3dCajZ3aHpKSmJqQmhSTg==")
            .build()
        chain.proceed(authorized)
    }.build())
    .build()


interface ApiService {
    @GET("countries")
    fun getCountriesAsync():
            Deferred<List<Country>>

    @GET("countries/IN/states")
    fun getStates():
            Deferred<List<StateResponse>>
}


/*
client.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
 */
object RegionApi {
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}