package com.falcon.unikit

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.preference.PreferenceManager
import com.falcon.unikit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity()  {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)



        binding.fab.setOnClickListener { view ->
            composeEmail("Regarding App " + getString(R.string.app_name))
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAnchorView(R.id.fab).setAction("Action", null).show()
        }

        binding.navView.setNavigationItemSelectedListener {
             if (it.itemId == R.id.getDirections) {
                 val intent = Intent(
                     Intent.ACTION_VIEW,
                     Uri.parse("http://maps.google.com/maps?34.34&daddr=28.66488568388205,77.30043327394083")
                 )
                 binding.drawerLayout.close()
                 startActivity(intent)
                 return@setNavigationItemSelectedListener true
            }
            else if (it.itemId == R.id.share) {
                 val sendIntent: Intent = Intent().apply {
                     action = Intent.ACTION_SEND
                     putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                     type = "text/plain"
                 }
                 val shareIntent = Intent.createChooser(sendIntent, null)
                 binding.drawerLayout.close()
                 startActivity(shareIntent)
                 return@setNavigationItemSelectedListener true
             }
            else if (it.itemId == R.id.reviewAndEarn) {
                 val url = "https://tinyurl.com/shikshausar"
                 val i = Intent(Intent.ACTION_VIEW)
                 i.data = Uri.parse(url)
                 binding.drawerLayout.close()
                 startActivity(i)
                 return@setNavigationItemSelectedListener true
             }
            val handled = NavigationUI.onNavDestinationSelected(it, navController)
            if (handled) {
                binding.drawerLayout.close()
            }
            handled
        }
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        var name = sharedPreferences.getString("name", "")
        name = if(name.isNullOrEmpty()) {
            getString(R.string.app_name)
        } else {
            "Hi, $name"
        }
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.userName).text = name


        // Code to remove toolbar if its the loginFragment/FirstFragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.loginFragment || destination.id == R.id.registerFragment || destination.id == R.id.walkThroughFragment) {
                binding.toolbar.visibility = View.GONE
            } else {
                binding.toolbar.visibility = View.VISIBLE
            }
        }
    }


    fun composeEmail(subject: String) {
        val a = arrayOf("usarcompanion@gmail.com")
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, a)
            putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No Mail App Found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(binding.drawerLayout)
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.close()
        } else {
            super.onBackPressed()
        }
    }
}