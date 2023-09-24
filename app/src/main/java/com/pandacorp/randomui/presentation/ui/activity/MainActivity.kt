package com.pandacorp.randomui.presentation.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.fragula2.animation.SwipeController
import com.fragula2.utils.findSwipeController
import com.pandacorp.randomui.R
import com.pandacorp.randomui.databinding.ActivityMainBinding
import com.pandacorp.randomui.presentation.ui.screen.MainScreen
import com.pandacorp.randomui.presentation.utils.helpers.PreferenceHandler
import com.pandacorp.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var fragulaNavController: NavController
    private lateinit var swipeController: SwipeController
    private var mainScreen: MainScreen? = null

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    fun getFragulaNavController(): NavController = fragulaNavController

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // Show Splash Screen
        PreferenceHandler.setLanguage(this)
        super.onCreate(savedInstanceState)
        // Throw any uncaught exceptions
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            throw (throwable)
        }
        PreferenceHandler.setTheme(this)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)

        initViews()
    }

    override fun onSupportNavigateUp(): Boolean {
        return fragulaNavController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        _binding = null
        mainScreen = null
        super.onDestroy()
    }

    private fun initViews() {
        binding.fragulaNavHostFragment.getFragment<NavHostFragment>().apply {
            swipeController = findSwipeController()
            fragulaNavController = navController
            val swipeBackFragment = childFragmentManager.fragments.first()
            swipeBackFragment.childFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentViewCreated(
                        fm: FragmentManager,
                        f: Fragment,
                        v: View,
                        savedInstanceState: Bundle?,
                    ) {
                        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                        if (f is MainScreen) mainScreen = f
                    }
                },
                false,
            )
        }

        binding.navView.apply {
            if (checkedItem == null) setCheckedItem(R.id.nav_one)
            setNavigationItemSelectedListener {
                lifecycleScope.launch {
                    delay(200) // add delay
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    delay(300) // add delay
                    when (it.itemId) {
                        R.id.nav_one -> mainScreen!!.navigateFragment(it.itemId)
                        R.id.nav_many -> mainScreen!!.navigateFragment(it.itemId)
                        R.id.nav_coin -> mainScreen!!.navigateFragment(it.itemId)

                        R.id.nav_settings ->
                            fragulaNavController.navigate(R.id.nav_settings_screen)
                    }
                }
                true
            }
        }

        // Animate arrow icon
        DrawerArrowDrawable(this@MainActivity).also { arrow ->
            binding.toolbar.navigationIcon = arrow.apply {
                color = Color.WHITE
            }
            binding.toolbar.setNavigationOnClickListener {
                when (arrow.progress) {
                    0f -> binding.drawerLayout.openDrawer(GravityCompat.START)
                    1f -> fragulaNavController.popBackStack()
                }
            }
            swipeController.addOnSwipeListener { position, positionOffset, _ ->
                arrow.progress = if (position > 0) 1f else positionOffset
                if (position > 0) {
                    binding.drawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                        GravityCompat.START,
                    )
                } else {
                    binding.drawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_UNLOCKED,
                        GravityCompat.START,
                    )
                }
            }
        }
    }
}