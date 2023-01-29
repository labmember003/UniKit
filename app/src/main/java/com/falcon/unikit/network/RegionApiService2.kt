//package com.falcon.unikit.network
//
//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
//import com.squareup.moshi.Moshi
//import kotlinx.coroutines.Deferred
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.http.GET
//
//private const val BASE_URL = "https://api.geonames.org/"
//
//private val moshi = Moshi.Builder().build()
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .addCallAdapterFactory(CoroutineCallAdapterFactory())
//    .baseUrl(BASE_URL)
//    .client(OkHttpClient().newBuilder().hostnameVerifier { hostname, _ ->
//        println(hostname) //To be hardcoded/as needed
//        true
//    }.build())
//    .build()
//
//private const val additionalURL = "countryInfoJSON?username=alice"
//
//interface ApiService {
//    @GET("countryInfoJSON?username=alice")
//    fun getCountriesAsync():
//            Deferred<CountriesResponse>
//
//    @GET("geonameId=1269750&$additionalURL")
//    fun getStates():
//            Deferred<StateResponse>
//}
//object RegionApi {
//    val apiService: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }
//}

/*
client.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
 */
