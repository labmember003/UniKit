package com.falcon.unikit

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.DownloadManager
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.falcon.unikit.databinding.ActivityContentBinding
import com.falcon.unikit.network.Subject
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import kotlin.math.absoluteValue


class ContentActivity : AppCompatActivity() {

private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        //ActivityCompat.requestPermissions(this,,1)
        // Function to check and request permission.

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this, "bro Permission tho de", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this,
                arrayOf(WRITE_EXTERNAL_STORAGE),
                12);
        } else {
            //Toast.makeText(this, "Permission mil gyi ", Toast.LENGTH_SHORT).show()
            //startDownloading()
        }

        // This function is called when the user accepts or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when the user is prompt for permission.



        //
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navView: BottomNavigationView = binding.navView
        navView.itemIconTintList = null
        val result = intent.extras?.getSerializable("CurrentSubject") as Subject
        Log.i("aapkimausi", result.subjectName)

        val bundleNotes = Bundle(); val bundlePaper = Bundle()
        val bundleBooks = Bundle(); val bundlePlaylist = Bundle(); val bundleSyallabus = Bundle()
        //bundleNotes.putSerializable("Notes", result.sections[0]) // 0 for notes and files 1 for papers and 2 for books
        val bundleKeyList = listOf<String>("Notes", "papers", "books", "Playlists", "Syllabus")
        val bundleList = listOf<Bundle>(bundleNotes, bundlePaper, bundleBooks, bundlePlaylist, bundleSyallabus)
        for (i in 0..4) {
            bundleList[i].putSerializable(bundleKeyList[i], result.sections[i])
            // if array pass krni ho tho :-
            //bundleList[i].putSerializable(bundleKeyList[i],ArrayList(result.sections))
            //bundleList[i].putSerializable(bundleKeyList[i],result.sections.toTypedArray())
            // this works because ArrayList aur TypedArray internally implements serializable
        }
        //val b = Bundle()
        //b.putSerializable("ma", result.sections[0].contents[0])
        //b.putParcelableArrayList("Product", result.sections[0])

        //

        val navController = findNavController(R.id.nav_host_fragment_contentActivity).also {
            it.setGraph(R.navigation.mobile_navigation, bundleNotes)
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation1, R.id.navigation2, R.id.navigation3, R.id.navigation4, R.id.navigation5))
        setupActionBarWithNavController(navController, appBarConfiguration)
        // navView.setupWithNavController(navController)
        Log.i("aapkimausi", result.sections[0].title)
        /*
        navView.setOnNavigationItemSelectedListener { item ->
            /*
            if (item.itemId == R.id.navigation1) {
                navController.navigate(item.itemId, bundleNotes)
            }
             */
            navController.navigate(item.itemId, bundleNotes)
            true
        }
         */

        navView.setOnItemSelectedListener { item ->
            //when (item.itemId) { }
            if (item.itemId == R.id.navigation1) {
                navController.navigate(item.itemId, bundleNotes)
            } else if (item.itemId == R.id.navigation2) {
                navController.navigate(item.itemId, bundlePaper)
            } else if (item.itemId == R.id.navigation3) {
                navController.navigate(item.itemId, bundleBooks)
            } else if (item.itemId == R.id.navigation4) {
                navController.navigate(item.itemId, bundlePlaylist)
            } else if (item.itemId == R.id.navigation5) {
                navController.navigate(item.itemId, bundleSyallabus)
            }
            true
        }


    }
/*
    fun startDownloading2(fileURL: String, titleAndFileName: String) {
        //val url : String = fileURL
        val uri = Uri.parse(fileURL)
        val request = DownloadManager.Request(Uri.parse(fileURL))
        //registerReceiver(, )
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(titleAndFileName)
        request.setDescription("File is donwloading")
        //request.setMimeType("application/pdf")
        request.setMimeType(getMimeType(fileURL))
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, titleAndFileName)

        // Check if file exists
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), titleAndFileName)

        if (file.exists()) {
            // Lauch INTENT of that file
            //openFile2(titleAndFileName, fileURL, file)
            //titleAndFileName
            //Toast.makeText(this, titleAndFileName, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "exists", Toast.LENGTH_SHORT).show()
            //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        } else {
            // Download
            Toast.makeText(this, "not exists", Toast.LENGTH_LONG).show()
            Toast.makeText(baseContext, "Download has begun, See Notifications", Toast.LENGTH_LONG).show()
            val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)
        }
    }

 */

    private fun getMimeType(fileURL: String): String {
        val type = getType(fileURL)
        return when(type) {
            "pdf" -> "application/pdf"
            "doc" -> "application/msword"
            "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            "ppt" -> "application/vnd.ms-powerpoint"
            "pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation"
            "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            "xls" -> "application/vnd.ms-excel"
            "jpeg" -> "image/jpeg"
            "jpg" -> "image/jpeg"
            "txt" -> "text/plain"
            "htm" -> "text/html"
            "html" -> "text/html"
            "mp4" -> "video/mp4"
            "mp3" -> "audio/mpeg"
            "zip" -> "application/zip"
            else -> {
                Toast.makeText(baseContext, "UNKNOWN FILE TYPE, PLEASE CONTACT DEVELOPER", Toast.LENGTH_LONG).show()
                ""
            }
        }
    }

    private fun getType(fileURL: String): String {
        for (i in (fileURL.indices).reversed()) {
            if (fileURL[i] == '.') {
                return fileURL.subSequence(i + 1, (fileURL.length)).toString()
            }
        }
        return ""
    }

    /*
    fun startDownloading(fileURL: String, titleAndFileName: String) {
        val url : String = "https://github.com/labmember003/usar_data/raw/master/YEAR_1/Sem1/CommunicationSkills/Exam/MinorExam.pdf"
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("gggg")
        request.setDescription("File is donwloading")
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "mausi.pdf")

        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }
     */

    override fun onBackPressed() {
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 12) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun openFile(titleAndFileName: String, fileURL: String, file: File) {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + File.separator +
                    titleAndFileName
        )
        Toast.makeText(this, file.absolutePath, Toast.LENGTH_SHORT).show()

        val path = Uri.fromFile(file)
        //val path = file.absolutePath
        //val path = Environment.getExternalStorageDirectory().toString() + "/" + "Downloads" + "/" + titleAndFileName
        //val uri = Uri.parse(file.absolutePath)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.setDataAndType(path, "application/pdf")
        //intent.setType("application/pdf")
        this.startActivity(intent)

    }
    private fun openFile2(titleAndFileName: String, fileURL: String, file: File) {

        Toast.makeText(this, file.absolutePath, Toast.LENGTH_SHORT).show()

        val uri = Uri.fromFile(file)
        //val path = file.absolutePath
        //val path = Environment.getExternalStorageDirectory().toString() + "/" + "Downloads" + "/" + titleAndFileName
        //val uri = Uri.parse(file.absolutePath)
        val intent = Intent(Intent.ACTION_VIEW)

        intent.setDataAndType(uri, "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        //intent.setType("application/pdf")
        this.startActivity(intent)

    }
    // Request code for selecting a PDF document.
    fun startDownloading(fileURL: String, titleAndFileName: String) {
        val activity = this
        try {
            if (fileURL.isNotEmpty()) {
                activity.registerReceiver(
                    attachmentDownloadCompleteReceive, IntentFilter(
                        DownloadManager.ACTION_DOWNLOAD_COMPLETE
                    )
                )
                val request = DownloadManager.Request(Uri.parse(fileURL))
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                request.setTitle(titleAndFileName)
                request.setDescription("File is donwloading")
                //request.setMimeType("application/pdf")
                request.setMimeType(getMimeType(fileURL))
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, titleAndFileName)


                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), titleAndFileName)

                if (file.exists()) {
//                    Toast.makeText(this, "exists", Toast.LENGTH_SHORT).show()
                    val myIntent = Intent(Intent.ACTION_VIEW)
                    val fileProviderUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file)
                    myIntent.data = fileProviderUri
                    myIntent.setDataAndType(fileProviderUri, getMimeType(fileURL))
                    myIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    val j = Intent.createChooser(myIntent, "Choose an application to open with:")
                    startActivity(j)

                } else {


                    // Download
//                    Toast.makeText(this, "not exists", Toast.LENGTH_LONG).show()
                    Toast.makeText(
                        baseContext,
                        "Download has begun, See Notifications",
                        Toast.LENGTH_LONG
                    ).show()
                    val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    manager.enqueue(request)
                }

            }
        } catch (e: IllegalStateException) {
            Toast.makeText(
                activity,
                "Please insert an SD card to download file",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    var attachmentDownloadCompleteReceive: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
                val downloadId = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, 0
                )
                openDownloadedAttachment(context, downloadId)
            }
        }
    }
    private fun openDownloadedAttachment(context: Context, downloadId: Long) {
        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val query = DownloadManager.Query()
        query.setFilterById(downloadId)
        val cursor: Cursor = downloadManager.query(query)
        if (cursor.moveToFirst()) {
            val downloadStatus: Int =
                cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS).absoluteValue)
            val downloadLocalUri: String =
                cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI).absoluteValue)
            val downloadMimeType: String =
                cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE).absoluteValue)
            if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL && downloadLocalUri != null) {
                openDownloadedAttachment(context, Uri.parse(downloadLocalUri), downloadMimeType)
            }
        }
        cursor.close()
    }

    private fun openDownloadedAttachment(
        context: Context,
        attachmentUri: Uri,
        attachmentMimeType: String
    ) {
        var attachmentUri: Uri? = attachmentUri
        if (attachmentUri != null) {
            // Get Content Uri.
            if (ContentResolver.SCHEME_FILE == attachmentUri.scheme) {
                // FileUri - Convert it to contentUri.
                val file = File(attachmentUri.path)
                attachmentUri =
                    FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID +".provider", file)
            }
            val openAttachmentIntent = Intent(Intent.ACTION_VIEW)
            openAttachmentIntent.setDataAndType(attachmentUri, attachmentMimeType)
            openAttachmentIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            try {
                context.startActivity(openAttachmentIntent)
            } catch (e: ActivityNotFoundException) {

            }
        }
    }


}


