//package com.falcon.unikit.network
//
//import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
//import com.squareup.moshi.Moshi
//import kotlinx.coroutines.Deferred
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.http.GET
//
//private const val BASE_URL = "https://api.countrystatecity.in/v1/"
//
//private val moshi = Moshi.Builder().build()
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .addCallAdapterFactory(CoroutineCallAdapterFactory())
//    .baseUrl(BASE_URL)
//    .client(OkHttpClient().newBuilder().addInterceptor { chain: Interceptor.Chain ->
//            val original: Request = chain.request()
//            val authorized: Request = original.newBuilder()
//                .addHeader("X-CSCAPI-KEY", "R3FsUnlwVVpBSUE5RDVkTDRiZGlUSUVFT3dCajZ3aHpKSmJqQmhSTg==")
//                .build()
//            chain.proceed(authorized)
//        }.build())
//    .build()
//
//
//interface ApiService {
//    @GET("countries")
//    fun getCountriesAsync():
//        Deferred<List<Country>>
//
//    @GET("countries/IN/states")
//    fun getStates():
//            Deferred<List<StateResponse>>
//}
//
//
///*
//client.setHostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                return true;
//            }
//        });
// */
//object RegionApi {
//    val apiService: ApiService by lazy {
//        retrofit.create(ApiService::class.java)
//    }
//}