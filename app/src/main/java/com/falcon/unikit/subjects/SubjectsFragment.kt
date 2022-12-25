package com.falcon.unikit.subjects

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.unikit.databinding.FragmentSubjectsBinding
import com.falcon.unikit.model.Year
import com.falcon.unikit.network.Subject


class SubjectsFragment : Fragment()  {
    companion object {
        const val ARG_CURRENT_YEAR = "currentYear"
        const val ARG_CURRENT_BRANCH = "currentBranch"
    }

    private val viewModel: SubjectsViewModel by lazy {
        ViewModelProvider(this).get(SubjectsViewModel::class.java)
    }

    private var onPerformNavigation: ((subject: Subject) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSubjectsBinding.inflate(inflater)
        val currentYear = arguments?.getSerializable(ARG_CURRENT_YEAR) as Year
        val currentBranch = arguments?.getSerializable(ARG_CURRENT_BRANCH) as com.falcon.unikit.model.Branch
        viewModel.setCurrentData(
            SubjectsViewModelData(
                currentBranch = currentBranch,
                currentYear = currentYear
            )
        )
        binding.imagePendingAnimation.isVisible = true
        //Toast.makeText(context, "sbki mausi " + currentBranchForViewModel, Toast.LENGTH_LONG).show()
        Log.i("currentYear", currentYear.toString())
        //Toast.makeText(context, "mausi " + currentYear.toString(), Toast.LENGTH_LONG).show()
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        // Giving the binding access to the OverviewViewModel
        //_binding = FragmentOverviewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.rvSubjects.layoutManager = LinearLayoutManager(requireContext())

        viewModel.subjects.observe(viewLifecycleOwner) { subjects ->
            if (subjects == null) {
                return@observe
            }
            val oddSemesterSubjects = mutableListOf<Subject>()
            val evenSemesterSubjects = mutableListOf<Subject>()
            for (subject in subjects) {
                if (subject.semester == "odd") {
                    oddSemesterSubjects.add(subject)
                }
                else {
                    evenSemesterSubjects.add(subject)
                }
            }
            val adapter = SubjectAdapter(requireContext(), oddSemesterSubjects, evenSemesterSubjects, ::onSubjectClick)

            //val map = subjects.groupBy { if (it.semester == "odd") "o" else "e" }
            //val adapter = SubjectAdapter(requireContext(), map["o"] ?: listOf(), map["e"] ?: listOf())
            binding.rvSubjects.adapter = adapter

        }

        viewModel.isDataFetchSuccessful.observe(viewLifecycleOwner) {
            if (it) {
                binding.tvErrorMessage.isVisible = false
            } else {
                binding.tvErrorMessage.isVisible = true
                binding.imagePendingAnimation.isVisible = false
                binding.errorAnimation.isVisible = true
            }
        }

        viewModel.hideLoadAnimation.observe(viewLifecycleOwner) {
            if (it) {
                binding.imagePendingAnimation.isVisible = false
            }
        }
        return binding.root
    }

    private fun onSubjectClick(currentSubject: Subject) {
        onPerformNavigation?.invoke(currentSubject)
    }

    fun setOnPerformNavigation(onPerformNavigation: (subject: Subject) -> Unit) {
        this.onPerformNavigation = onPerformNavigation
    }
}


