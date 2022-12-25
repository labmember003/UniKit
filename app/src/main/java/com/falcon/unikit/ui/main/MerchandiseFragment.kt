package com.falcon.unikit.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.falcon.unikit.MyAdapter
import com.falcon.unikit.databinding.FragmentMerchandiseBinding
import com.falcon.unikit.network.Merge
import com.google.firebase.database.*

class MerchandiseFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private var _binding: FragmentMerchandiseBinding? = null
    private val binding get() = _binding!!
    private lateinit var  merchRecyclerView: RecyclerView
    private lateinit var  merchandiseList: ArrayList<Merge>

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMerchandiseBinding.inflate(inflater, container, false)
        merchRecyclerView = binding.merchandiseList
        merchRecyclerView.layoutManager = LinearLayoutManager(context)
        merchRecyclerView.setHasFixedSize(true)
        merchandiseList = arrayListOf<Merge>()
        //getUserData()
        context?.let {
//            if (!isOnline(it)) {
//                Toast.makeText(it, "NO INTERNET CONNECTION", Toast.LENGTH_LONG).show()
//            }
        }
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("merchList")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (merchSnapshot in snapshot.children) {
                        val catName = merchSnapshot.getValue(Merge::class.java)
                            catName?.let {
                                merchandiseList.add(it)
                        }
                    }
                    merchRecyclerView.adapter = MyAdapter(requireContext(), merchandiseList)
                    binding.imagePendingAnimation.visibility = View.INVISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        database.child("merchList").get()
    }
}