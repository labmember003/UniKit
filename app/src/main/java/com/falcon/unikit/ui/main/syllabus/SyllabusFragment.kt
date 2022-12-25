package com.falcon.unikit.ui.main.syllabus

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.unikit.ContentActivity
import com.falcon.unikit.databinding.FragmentSyllabusBinding
import com.falcon.unikit.network.Section
import com.falcon.unikit.ui.main.RcvContentAdapter

class SyllabusFragment : Fragment() {
    private var _binding: FragmentSyllabusBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val syllabusViewModel =
            ViewModelProvider(this).get(SyllabusViewModel::class.java)

        _binding = FragmentSyllabusBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*
      val textView: TextView = binding.textHome
      homeViewModel.text.observe(viewLifecycleOwner) {
        textView.text = it
      }
         */
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO mausi

        var syllabus : Section? = null
        if (arguments != null) {
            syllabus = arguments?.getSerializable("Syllabus") as Section?
            Log.i("billisyllabus", syllabus?.title.toString())
        }
        Log.i("billi","data reached in frag")
        //
        //TODO("HANDLE NULL")
        if (syllabus?.contents?.isEmpty() == true) {
            Log.i("emptyContent", "${syllabus.title} is empty")
            //binding.tvErrorMessageContents.
            binding.sale = true
        }

        val adapter = RcvContentAdapter(requireContext(), syllabus?.contents!!, syllabus.title, ::onContentClick)
        binding.rvContents.adapter = adapter
        binding.rvContents.layoutManager = LinearLayoutManager(requireContext())
        //

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    /*
        val callback2 = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_homeFragment_to_mainActivity3)
        }
     */
    fun onContentClick(fileURL: String, titleAndFileName: String) {
        (activity as ContentActivity?)!!.startDownloading(fileURL, titleAndFileName)
    }
    //requireActivity()

}