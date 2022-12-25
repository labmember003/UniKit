package com.falcon.unikit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.falcon.unikit.databinding.FragmentBranchesTabbedBinding
import com.falcon.unikit.ui.main.SectionsPagerAdapter
import com.falcon.unikit.ui.main.TAB_TITLES
import com.google.android.material.tabs.TabLayoutMediator

class BranchesTabbedFragment : Fragment() {

    private lateinit var binding: FragmentBranchesTabbedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val currentYear = arguments?.getInt("Year")
        //Toast.makeText(activity, "$currentYear", Toast.LENGTH_SHORT).show()
        binding = FragmentBranchesTabbedBinding.inflate(layoutInflater, container, false)
        val sectionsPagerAdapter = SectionsPagerAdapter(requireContext(), this, currentYear) { subject ->
            val bundle = Bundle()
            bundle.putSerializable("CurrentSubject", subject)
            findNavController().navigate(R.id.action_BranchesTabbedFragment_to_contentActivity, bundle)
        }
        val viewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = context?.getString(TAB_TITLES[position])
        }.attach()
        getActionBar()?.title = "Year " + currentYear.toString()
        return binding.root
    }

    private fun getActionBar(): androidx.appcompat.app.ActionBar? {
        return (activity as? MainActivity)?.supportActionBar
    }
}