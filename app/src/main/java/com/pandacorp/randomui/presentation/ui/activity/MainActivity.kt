package com.pandacorp.randomui.presentation.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
import com.pandacorp.randomui.presentation.di.app.App
import com.pandacorp.randomui.presentation.ui.screen.MainScreen
import com.pandacorp.randomui.presentation.utils.PreferenceHandler
import com.pandacorp.randomui.presentation.utils.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val app by lazy { application as App }

    private lateinit var fragulaNavController: NavController
    private lateinit var swipeController: SwipeController

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var mainScreen: MainScreen? = null

    fun getFragulaNavController(): NavController = fragulaNavController

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
                        savedInstanceState: Bundle?
                    ) {
                        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                        if (f is MainScreen) mainScreen = f
                    }

                }, false
            )
        }

        binding.navView.apply {
            setCheckedItem(app.selectedNavigationItemId)
            setNavigationItemSelectedListener {
                // Uncheck selectedNavigationItemId in App and set the previous one
                app.selectedNavigationItemId = it.itemId
                lifecycleScope.launch {
                    delay(200) // add delay
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    delay(300) // add delay
                    when (it.itemId) {
                        R.id.nav_one -> mainScreen!!.navigateFragment(it.itemId)
                        R.id.nav_many -> mainScreen!!.navigateFragment(it.itemId)
                        R.id.nav_coin -> mainScreen!!.navigateFragment(it.itemId)

                        R.id.nav_settings -> {
                            fragulaNavController.navigate(R.id.nav_settings_screen)
                            // Set the previous selected item
                            app.selectedNavigationItemId =
                                binding.navView.checkedItem?.itemId ?: R.id.nav_one

                        }

                        R.id.nav_share -> {
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.shareText))
                                type = "text/plain"
                            }
                            startActivity(Intent.createChooser(sendIntent, getString(R.string.share)))
                            // Set the previous selected item
                            app.selectedNavigationItemId =
                                binding.navView.checkedItem?.itemId ?: R.id.nav_one
                        }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // Handle the splash screen transition.
        super.onCreate(savedInstanceState)
        Utils.setupExceptionHandler()
        PreferenceHandler.load(this)
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
}