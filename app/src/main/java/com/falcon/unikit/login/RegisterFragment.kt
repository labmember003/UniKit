package com.falcon.unikit.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.falcon.unikit.databinding.FragmentRegisterBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.registerButton.setOnClickListener {
            registerUser()
        }
        return binding.root

    }

    private fun registerUser() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//            //findNavController().navigate(R.id.action_FirstFragment_to_campusMap)
//            //findNavController().navigate(R.id.action_FirstFragment_to_aboutFragment)
//            //findNavController().navigate(R.id.action_FirstFragment_to_yearTabbedActivity)
//            //findNavController().navigate(R.id.action_FirstFragment_to_overviewFragment)
//            //findNavController().navigate(R.id.action_FirstFragment_to_BranchesTabbedFragment)
//            findNavController().navigate(R.id.action_FirstFragment_to_resources)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
/*
// CODE TO LAUNCH USAR IN GOOGLE MAPS
val intent = Intent(
    Intent.ACTION_VIEW,
    Uri.parse("http://maps.google.com/maps?34.34&daddr=28.66488568388205, 77.30043327394083")
)
startActivity(intent)
 */