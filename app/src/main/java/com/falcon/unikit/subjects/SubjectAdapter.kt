package com.falcon.unikit.subjects

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.falcon.unikit.R
import com.falcon.unikit.network.Subject


private const val TYPE_HEADLINE = 0
private const val TYPE_SUBJECT = 1

class HeadlineItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
}


class SubjectItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var subjectIcon: ImageView = itemView.findViewById(R.id.subjectIcon)
    var subjectName: TextView = itemView.findViewById(R.id.subjectName)
}


class SubjectAdapter(
    val context: Context,
    private val oddSemesterSubjects: List<Subject>,
    private val evenSemesterSubjects: List<Subject>,
    private val onSubjectClick: (Subject) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var lastPosition : Int = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADLINE -> HeadlineItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_headline_item_layout, parent, false))
            TYPE_SUBJECT -> SubjectItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_subjects_item_layout, parent, false))
            else -> throw IllegalStateException("Unknown viewType: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADLINE
            isOddSemester(position) -> TYPE_SUBJECT
            (position == oddSemesterSubjects.size + 1) -> TYPE_HEADLINE
            else -> TYPE_SUBJECT
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.rootView.setOnClickListener{
            /*
            NAVIGATION KA CODE (OBVIOUSLY WITHOUT ARGUMENTS)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.FirstFragment)
             */
            if (getItemViewType(position) == TYPE_SUBJECT) {
                onSubjectClick(getSubject(position))
            }
        }
        when(holder) {
            is HeadlineItemViewHolder -> {
                holder.tvTitle.text = if (position == 0) "Semester 1" else "Semester 2"
            }
            is SubjectItemViewHolder -> {
                val subject = if (isOddSemester(position)) {
                    oddSemesterSubjects[position - 1]
                } else {
                    evenSemesterSubjects[position - (oddSemesterSubjects.size + 2)]
                }

                holder.subjectName.text = subject.subjectName

                val imgUri = subject.iconUrl.toUri().buildUpon().scheme("https").build()
                Glide.with(context)
                    .load(imgUri)
                    //.fitCenter()
                    .transition(withCrossFade(
                        DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                    ))
                    .placeholder(R.drawable.circle)
                    .into(holder.subjectIcon)
            }
            else -> throw IllegalStateException("Unknown holder: $holder")
        }
        setAnimation(holder.itemView.rootView, position)
    }


    private fun getSubject(position: Int) : Subject {
        return if (isOddSemester(position)) {
            oddSemesterSubjects[position - 1]
        } else {
            evenSemesterSubjects[(position - oddSemesterSubjects.size) - 2]
        }
    }

    private fun isOddSemester(position: Int): Boolean {
        return (position - 1) < oddSemesterSubjects.size
    }

    override fun getItemCount(): Int {
        return oddSemesterSubjects.size + evenSemesterSubjects.size + 2
    }
    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}