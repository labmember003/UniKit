package com.falcon.unikit.ui.main.playlists

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
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
import com.falcon.unikit.databinding.FragmentPlaylistsBinding
import com.falcon.unikit.network.Section
import com.falcon.unikit.ui.main.RcvContentAdapter
import java.io.File


class PlaylistsFragment : Fragment() {
    private var _binding: FragmentPlaylistsBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: PlaylistsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val playlistsViewModel =
            ViewModelProvider(this).get(PlaylistsViewModel::class.java)
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO mausi

        var playlists : Section? = null
        if (arguments != null) {
            playlists = arguments?.getSerializable("Playlists") as Section?
            Log.i("billiplaylists", playlists?.title.toString())
        }
        Log.i("billi","data reached in frag")
        //
        //TODO("HANDLE NULL")
        if (playlists?.contents?.isEmpty() == true) {
            Log.i("emptyContent", "${playlists.title} is empty")
            //binding.tvErrorMessageContents.
            binding.sale = true
        }

        val adapter = RcvContentAdapter(requireContext(), playlists?.contents!!, playlists.title, ::onContentClick, ::shareFile)
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
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, fileName)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
    private fun onContentClick(fileURL: String, titleAndFileName: String) {
        openPlaylist(fileURL)
    }
    private fun openPlaylist (fileURL: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(fileURL))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(fileURL)
        )
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }
    }

}