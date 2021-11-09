package com.example.eproject

import android.content.Intent
import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewbinding.ViewBinding
import com.example.eproject.databinding.ActivityHomeBinding
import com.example.eproject.models.ProjectModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import java.util.Calendar.JANUARY
import java.util.Calendar.MAY
import kotlin.collections.ArrayList


class Home : AppCompatActivity() {
    //private lateinit var layout:Home
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var projectList: ArrayList<ProjectModel>
    private lateinit var projectAdapter: ProjectAdapter
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //layout  = Home()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
      // val recyclerView = project_list
      //  val itemDecor = DividerItemDecoration(applicationContext,HORIZONTAL)
      //  recyclerView.addItemDecoration(itemDecor)
        projectList = ArrayList()
        var x = 1
        while(x<45){
        //    project()
            x++
        }
     //   projectAdapter = ProjectAdapter(projectList)
//        recyclerView.adapter = projectAdapter


//       val fab: FloatingActionButton = findViewById(R.id.fab)
    /*   binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        val drawerLayout: DrawerLayout = binding.drawerLayout
       val navView: NavigationView = binding.navView
     val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.home,R.id.notifications,R.id.settings,R.id.locations
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_settings -> Toast.makeText(applicationContext,"Setting",5).show()
            R.id.switch_account -> Toast.makeText(applicationContext,"Switching Account",5).show()
            R.id.category -> Toast.makeText(applicationContext,"Select a category",5).show()
            R.id.logout ->{
                Toast.makeText(applicationContext,"Logging out",5).show()
                val logout = Intent(this,MainActivity::class.java)
                startActivity(logout)
            }
            R.id.status -> Toast.makeText(applicationContext,"Status",5).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun project(){
        projectList.add(ProjectModel("Free Water","Make water accessible in rural region", Date(2021,JANUARY,15),Date(2025,
            MAY,27),"World Bank",R.drawable.eproject,1))
        projectList.add(ProjectModel("Free Electricity","Make electricity accessible in rural region", Date(2021,JANUARY,15),Date(2025,
            MAY,27),"African Bank of Development",R.drawable.energy,2))
    }
}