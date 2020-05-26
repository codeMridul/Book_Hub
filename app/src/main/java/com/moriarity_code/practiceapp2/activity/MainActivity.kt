package com.moriarity_code.practiceapp2.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.moriarity_code.practiceapp2.R
import com.moriarity_code.practiceapp2.fragment.AboutAppFragment
import com.moriarity_code.practiceapp2.fragment.DashboardFragment
import com.moriarity_code.practiceapp2.fragment.FavouriteFragment
import com.moriarity_code.practiceapp2.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    lateinit var btnLogout: Button

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    lateinit var sharedPreferences: SharedPreferences

    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)


        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frame)
        navigationView = findViewById(R.id.navigationView)
        setToolbar()

        openDashboard()  //opening dashboard as the default fragment

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()



        navigationView.setNavigationItemSelectedListener {
            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.dashboard -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            DashboardFragment()
                        )
                        .addToBackStack("Dashboard")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "Book Hub"

                    Toast.makeText(
                        this@MainActivity,
                        "Clicked on the Dashboard",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.favourites -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            FavouriteFragment()
                        )
                        .addToBackStack("Favourites")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "Favourites"
                    Toast.makeText(
                        this@MainActivity,
                        "Clicked on the Favourites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            ProfileFragment()
                        )
                        .addToBackStack("Profile")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = "Profile"
                    Toast.makeText(
                        this@MainActivity,
                        "Clicked on the Profile",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.aboutus -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            AboutAppFragment()
                        )
                        .addToBackStack("About Us")
                        .commit()
                    drawerLayout.closeDrawers()
                    supportActionBar?.title = getString(R.string.about_app)
                    Toast.makeText(
                        this@MainActivity,
                        "Clicked on the About Us",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.logout -> {
                    sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
                    val intent = Intent(
                        this@MainActivity,
                        LoginActivity::class.java
                    )
                    startActivity(intent)
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Code.Moriarity"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        };
        return super.onOptionsItemSelected(item)
    }

    fun openDashboard() {
        val fragment = DashboardFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.commit()
        supportActionBar?.title = "Book Hub"
        navigationView.setCheckedItem(R.id.dashboard)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.frame)
        when (frag) {
            !is DashboardFragment -> openDashboard()
            else -> super.onBackPressed()
        }
    }
}
