package com.example.eproject.ui.home

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.eproject.ProjectAdapter
import com.example.eproject.ProjectViewModel
import com.example.eproject.R
import com.example.eproject.databinding.FragmentHomeBinding
import com.example.eproject.models.ProjectModel
import java.util.*
import java.util.Calendar.JANUARY
import java.util.Calendar.MAY
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var projectList: ArrayList<ProjectModel>
    private lateinit var projectAdapter: ProjectAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerview:RecyclerView = binding.projectlistrecyclerview123
        projectList = ArrayList()
       project()
        projectAdapter = ProjectAdapter(projectList)
        recyclerview.adapter = projectAdapter

        return binding.root
    }
    private fun project(){
        var project1 = ProjectModel("Free Water","Make water accessible in rural region", Date(2021,JANUARY,15),
            Date(2025,
                MAY,27),"World Bank",R.drawable.eproject,10)
        projectList.add(project1)
        projectList.add(ProjectModel("Free Water","Make water accessible in rural region", Date(2021,JANUARY,15),
            Date(2025,
                MAY,27),"World Bank",R.drawable.eproject,10))
        projectList.add(ProjectModel("Free Electricity","Make electricity accessible in rural region", Date(2021,JANUARY,15),Date(2025,
            MAY,27),"African Bank of Development",R.drawable.energy,11))
    }
       // val textView: TextView = root.findViewById(R.id.text_home)
       /* homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
     /*   projectList = ArrayList()
        var x = 1
        while(x<45){
            project()
            x++
        }

      //  getProject()
        return root

    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getProject() {
        val viewModel: ProjectViewModel =
            ViewModelProvider(this).get(ProjectViewModel::class.java)
        viewModel.getLiveDataObserver().observe(viewLifecycleOwner, Observer {
            if (it != null) {
         //       projectAdapter.setProjectLists(it)
                Log.i("data", "" + it.size)
                projectAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getProject()
    }
     
}