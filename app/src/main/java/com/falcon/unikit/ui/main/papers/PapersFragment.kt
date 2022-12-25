package com.falcon.unikit.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.unikit.ContentActivity
import com.falcon.unikit.databinding.FragmentPapersBinding
import com.falcon.unikit.network.Section
import com.falcon.unikit.ui.main.RcvContentAdapter

class PapersFragment : Fragment() {

private var _binding: FragmentPapersBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val dashboardViewModel =
            ViewModelProvider(this).get(PapersViewModel::class.java)

    _binding = FragmentPapersBinding.inflate(inflater, container, false)
    val root: View = binding.root
    /*
    val textView: TextView = binding.textDashboard
    dashboardViewModel.text.observe(viewLifecycleOwner) {
      textView.text = it
    }
    */
    return root
  }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val papers : Section?
        if (arguments != null) {
            papers = arguments?.getSerializable("papers") as Section?
            Log.i("billipaper", papers?.title.toString())
        }
        Log.i("billi","data reached in frag")
    }
 */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var papers : Section? = null
        if (arguments != null) {
            papers = arguments?.getSerializable("papers") as Section?
            Log.i("billipapers", papers?.title.toString())
        }
        Log.i("billi","data reached in frag")
        //
        //TODO("HANDLE NULL")
        if (papers?.contents?.isEmpty() == true) {
            Log.i("emptyContent", "${papers.title} is empty")
            //binding.tvErrorMessageContents.
            binding.sale = true
        }

        val adapter = RcvContentAdapter(requireContext(), papers?.contents!!, papers.title, ::onContentClick)
        binding.rvContentsPapers.adapter = adapter
        binding.rvContentsPapers.layoutManager = LinearLayoutManager(requireContext())


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun onContentClick(fileURL: String, titleAndFileName: String) {
        (activity as ContentActivity?)!!.startDownloading(fileURL, titleAndFileName)
    }
}