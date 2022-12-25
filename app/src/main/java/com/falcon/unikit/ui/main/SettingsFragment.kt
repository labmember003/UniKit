package com.falcon.unikit.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.falcon.unikit.MainActivity
import com.falcon.unikit.R
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity


class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceClickListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(com.falcon.unikit.R.xml.root_preferences, rootKey)
        val preference = preferenceManager.findPreference<Preference>("libraries")
        preference?.setOnPreferenceClickListener {
            startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            true
        }
        val preferenceBugReport = preferenceManager.findPreference<Preference>("bug")
        preferenceBugReport?.setOnPreferenceClickListener {
            (activity as MainActivity).composeEmail("Bug Report For " + getString(R.string.app_name))
            true
        }
        val preferenceContact = preferenceManager.findPreference<Preference>("contact")
        preferenceContact?.setOnPreferenceClickListener {
            (activity as MainActivity).composeEmail("Regarding App " + getString(R.string.app_name))
            true
        }
    }

    override fun onPreferenceClick(preference: Preference): Boolean {
        //Toast.makeText(context, "hemlo", Toast.LENGTH_SHORT).show()
        return true
    }
}
/*
val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
val name = sharedPreferences.getString("name", "")
Toast.makeText(requireContext(), name, Toast.LENGTH_SHORT).show()
 */