package com.falcon.unikit.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.falcon.unikit.R
import com.falcon.unikit.model.Branch
import com.falcon.unikit.model.Year
import com.falcon.unikit.network.Subject
import com.falcon.unikit.subjects.SubjectsFragment

val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3,
    R.string.tab_text_4
)

val TAB_BRANCHES = arrayOf(
    "AIDS",
    "AIML",
    "IIOT",
    "AR"
)

class SectionsPagerAdapter(
    private val context: Context,
    fragment: Fragment,
    private val currentYear: Int?,
    private val onSubjectClick: (subject: Subject) -> Unit
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            else -> {
                val b = Bundle()
                if (currentYear != null) {
                    b.putSerializable(SubjectsFragment.ARG_CURRENT_YEAR, Year.getYear(currentYear))
                    //currentBranch
                    //TAB_TITLES[position]
                    //contextForViewModel?.resources?.getString(R.string.tab_text_1)
                    //contextForSubjectAdapterAndViewModel?.resources?.getString(TAB_TITLES[position])
                    b.putSerializable(SubjectsFragment.ARG_CURRENT_BRANCH, Branch.getBranch(TAB_BRANCHES[position]))
                    //b.putString("currentBranch", contextForSubjectAdapterAndViewModel?.resources?.getString(TAB_TITLES[position]))
                }
                val fragment = SubjectsFragment()
                fragment.arguments = b // Passing Current Year to SubjectsFragment
                fragment.setOnPerformNavigation(onSubjectClick)
                fragment
            }
        }
    }

}
