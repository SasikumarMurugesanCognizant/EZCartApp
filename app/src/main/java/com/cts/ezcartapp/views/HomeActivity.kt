package com.cts.ezcartapp.views

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.cts.ezcartapp.R
import com.cts.ezcartapp.databinding.ActivityHomeBinding
import com.cts.ezcartapp.utils.SharedPreference
import com.google.android.material.navigation.NavigationView

import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.cts.ezcartapp.data.repository.RemoteDataSourceRepository
import com.cts.ezcartapp.utils.Constants.userName
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    HasAndroidInjector {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    lateinit var hostFragment: NavHostFragment

    @Inject
    lateinit var sharedPref: SharedPreference

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var remoteDataSourceRepository:RemoteDataSourceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarHome.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarHome.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)
        hostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_homes) as NavHostFragment
        NavigationUI.setupWithNavController(binding.navView, hostFragment.navController)
        //NavigationUI.setupActionBarWithNavController(this@HomeActivity, hostFragment.navController)
        NavigationUI.setupActionBarWithNavController(
            this@HomeActivity,
            hostFragment.navController,
            binding.drawerLayout
        )
        val tvUserName = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_username)
        tvUserName.text = sharedPref.getFullName(userName)

        binding.navView.menu.findItem(R.id.nav_logout)
            .setOnMenuItemClickListener { menuItem: MenuItem? ->
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                showWarningDialog()
                true
            }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            hostFragment.navController,
            binding.drawerLayout
        ) || super.onSupportNavigateUp()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showWarningDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Logout")
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setMessage("Are you sure you want to Logout?")
        alertDialogBuilder.setPositiveButton("Yes") { dialogInterface: DialogInterface, _ ->
            sharedPref.clearSharedPreference()
            clearShoppingList()
            dialogInterface.dismiss()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return fragmentDispatchingAndroidInjector
    }

    fun clearShoppingList() {
        this.lifecycleScope.launch {
            remoteDataSourceRepository.clearShoppingListFromLocalStorage()
        }
    }

}