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

var countryCode = ""

var listOfCollege = listOf(
    "Ambedkar Institute of Technology",
    "Amity School of Engineering & Technology	",
    "Bhagwan Parshuram Institute of Technology	",
    "Bharati Vidyapeeth's College of Engineering	",
    "CBP Government Engineering College",
    "Delhi Institute of Tool Engineering",
    "Delhi Technological University",
    "Faculty of Engineering and Technology JMI",
    "GB Pant Government Engineering College, Delhi",
    "Govind Ballabh Pant Engineering College",
    "Guru Premsukh Memorial College of Engineering",
    "Guru Tegh Bahadur Institute of Technology",
    "HMR Institute of Technology & Management",
    "Indian Institute of Technology, Delhi	",
    "Indira Gandhi Delhi Technical University for women",
    "Indraprastha Institute of Information Technology",
    "JIMS Engineering Management Technical Campus",
    "Maharaja Agrasen Institute of Technology",
    "Maharaja Surajmal Institute of Technology",
    "National Institute of Technology, Delhi",
    "National Power Training Institute",
    "Netaji Subhas University of Technology",
    "Northern India Engineering College",
    "Pt. Deendayal Upadhyaya Institute for the Physically Handicapped",
    "School of Engineering science and Technology",
    "School of Planning and Architecture",
    "TVB School of Habitat Studies",
    "University School of Automation and Robotics",
    "University School of Biotechnology",
    "University School of Chemical Technology",
    "University School of Information Technology (USIT)",
    "Vastu Kala Academy"
)

var listOfCourses = listOf(
    "Computer Science Engineering",
    "Mechanical Engineering",
    "Electronics and Communication Engineering",
    "Electrical Engineering",
    "Electrical and Electronics Engineering",
    "Civil Engineering",
    "Chemical Engineering",
    "Information Technology",
    "Instrumentation and Control Engineering",
    "Electronics Engineering",
    "Electronics and Telecommunication Engineering",
    "Petroleum Engineering",
    "Aeronautical Engineering",
    "Aerospace Engineering",
    "Automobile Engineering",
    "Mining Engineering",
    "Power Engineering",
    "Production Engineering",
    "Biotechnology Engineering",
    "Genetic Engineering",
    "Plastics Engineering",
    "Food Processing and Technology",
    "Agricultural Engineering",
    "Environmental Engineering",
    "Dairy Technology and Engineering",
    "Agricultural Information Technology",
    "Infrastructure Engineering",
    "Motorsport Engineering",
    "Metallurgy Engineering",
    "Textile Engineering",
    "Marine Engineering",
    "Naval Architecture",
    "Geoinformatics",
    "Petrochemical Engineering",
    "Polymer Engineering",
    "Geotechnical Engineering",
    "Nuclear Engineering",
    "Artificial Intelligence & Data Science",
    "Artificial Intelligence & Machine Learning",
    "Industrial Internet of Things",
    "Automation & Robotics"
)

var listOfGraduationYear = listOf(
    "2023",
    "2024",
    "2025",
    "2026",
    "2027",
    "2028",
)

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
        val adapter3 = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, listOfCollege)
        binding.collegeAutoCompleteTextView.setAdapter(adapter3)
        val adapter4 = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, listOfCourses)
        binding.coursesAutoCompleteTextView.setAdapter(adapter4)
        val adapter5 = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, listOfGraduationYear)
        binding.graduationYearAutoCompleteTextView.setAdapter(adapter5)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





