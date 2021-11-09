package com.example.eproject

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eproject.models.ProjectModel
import com.squareup.picasso.Picasso

class ProjectAdapter(private val projectLists: ArrayList<ProjectModel>):
    RecyclerView.Adapter<ProjectAdapter.MyViewHolder>() {
    class MyViewHolder(itemRow: View) : RecyclerView.ViewHolder(itemRow) {
        val project_id: TextView = itemRow.findViewById(R.id.project_id)
        val image: ImageView = itemRow.findViewById(R.id.project_image)
        val projectName: TextView = itemRow.findViewById(R.id.project_name)
        val purpose: TextView = itemRow.findViewById(R.id.project_purpose)
        val startingDate: TextView = itemRow.findViewById(R.id.project_starting)
        val endingDate: TextView = itemRow.findViewById(R.id.project_ending)
        val funder: TextView = itemRow.findViewById(R.id.project_funder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemRow =
            LayoutInflater.from(parent.context).inflate(R.layout.presentation, parent, false)
        return MyViewHolder(itemRow)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentProject = projectLists[position]
        holder.projectName.text = currentProject.projectName
        holder.project_id.text = currentProject.id.toString()
        holder.purpose.text = currentProject.purpose
        holder.startingDate.text = currentProject.startingDate.toString()
        holder.endingDate.text = currentProject.endingDate.toString()
        holder.funder.text = currentProject.funder
        holder.image.setImageResource(currentProject.image!!)
    }

    override fun getItemCount(): Int {
        return projectLists.size
    }
}

