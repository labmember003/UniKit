package com.falcon.unikit.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.falcon.unikit.ContentActivity
import com.falcon.unikit.databinding.FragmentNotesBinding
import com.falcon.unikit.network.Section
import com.falcon.unikit.ui.main.RcvContentAdapter
import java.io.File

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(NotesViewModel::class.java)

        _binding = FragmentNotesBinding.inflate(inflater, container, false)
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

        var notes : Section? = null
        if (arguments != null) {
            notes = arguments?.getSerializable("Notes") as Section?
            Log.i("billinotes", notes?.title.toString())
        }
        Log.i("billi","data reached in frag")
        //
        //TODO("HANDLE NULL")
        if (notes?.contents?.isEmpty() == true) {
            Log.i("emptyContent", "${notes.title} is empty")
            //binding.tvErrorMessageContents.
            binding.sale = true
        }

        val adapter = RcvContentAdapter(requireContext(), notes?.contents!!, notes.title, ::onContentClick, ::shareFile)
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
    private fun shareFile(fileName: String) {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)
        if (file.exists()) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "*/*"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val photoURI = FileProvider.getUriForFile(
                requireContext(),
                requireContext().applicationContext.packageName + ".provider",
                file
            )
            intent.putExtra(Intent.EXTRA_STREAM, photoURI)
            startActivity(Intent.createChooser(intent, "Share File"))
        } else {
            Toast.makeText(requireContext(), "First Download the file", Toast.LENGTH_SHORT).show()
        }
    }
    private fun onContentClick(fileURL: String, titleAndFileName: String) {
        (activity as ContentActivity?)!!.startDownloading(fileURL, titleAndFileName)
    }
    //requireActivity()

}