package com.pandacorp.randomui.presentation.ui.screen

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.pandacorp.randomui.R
import com.pandacorp.randomui.databinding.ScreenSettingsBinding
import com.pandacorp.randomui.presentation.utils.dialogs.SettingsDialog
import com.pandacorp.randomui.presentation.utils.helpers.Constants
import com.pandacorp.randomui.presentation.utils.helpers.getPackageInfoCompat
import com.pandacorp.randomui.presentation.utils.helpers.supportActionBar
import com.pandacorp.randomui.presentation.utils.helpers.viewBinding

class SettingsScreen : Fragment(R.layout.screen_settings) {
    private val binding by viewBinding(ScreenSettingsBinding::bind)

    private val sp: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(requireContext())
    }

    private val languageDialog by lazy {
        SettingsDialog(requireActivity(), Constants.PreferenceKeys.languagesKey)
    }
    private val themeDialog by lazy {
        SettingsDialog(requireActivity(), Constants.PreferenceKeys.themesKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val dialogKey =
            if (themeDialog.isShowing) Constants.PreferenceKeys.themesKey
            else if (languageDialog.isShowing) Constants.PreferenceKeys.languagesKey
            else null

        outState.apply {
            putString(Constants.PreferenceKeys.preferenceBundleKey, dialogKey)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        when (savedInstanceState?.getString(Constants.PreferenceKeys.preferenceBundleKey, null)) {
            Constants.PreferenceKeys.themesKey -> themeDialog.show()
            Constants.PreferenceKeys.languagesKey -> languageDialog.show()
            null -> Unit
        }
    }

    override fun onResume() {
        super.onResume()
        supportActionBar?.setTitle(R.string.settings)
    }

    private fun initViews() {
        // Retrieve the version from build.gradle and assign it to the TextView
        try {
            val version =
                requireContext().packageManager.getPackageInfoCompat(requireContext().packageName).versionName

            @SuppressLint("StringFormatMatches") // Android Studio bug(probably), though the code works fine
            val stringVersion = resources.getString(R.string.version, version)
            binding.versionTextView.text = stringVersion
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        binding.themeLayout.setOnClickListener {
            themeDialog.show()
        }
        binding.languageLayout.setOnClickListener {
            languageDialog.show()
        }
        binding.themeTextView.apply {
            val themeKey = sp.getString(
                Constants.PreferenceKeys.themesKey,
                requireContext().resources.getString(R.string.settings_theme_default_value)
            )!!
            text = getThemeFromKey(themeKey)
        }
        binding.languageTextView.apply {
            val languageKey = sp.getString(
                Constants.PreferenceKeys.languagesKey,
                requireContext().resources.getString(R.string.settings_language_default_value)
            )!!
            text = getLanguageFromKey(languageKey)
        }
    }

    private fun getThemeFromKey(key: String): String {
        val themes = resources.getStringArray(R.array.Themes)
        val keys = resources.getStringArray(R.array.Themes_values)
        return themes[keys.indexOf(key)]
    }

    private fun getLanguageFromKey(key: String): String {
        val languages = resources.getStringArray(R.array.Languages)
        val keys = resources.getStringArray(R.array.Languages_values)
        return languages[keys.indexOf(key)]
    }
}