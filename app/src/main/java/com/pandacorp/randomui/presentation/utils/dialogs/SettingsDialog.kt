package com.pandacorp.randomui.presentation.utils.dialogs

import android.app.Activity
import android.os.Bundle
import com.pandacorp.randomui.R
import com.pandacorp.randomui.databinding.DialogListViewBinding
import com.pandacorp.randomui.presentation.ui.adapter.SettingsAdapter
import com.pandacorp.randomui.presentation.ui.adapter.SettingsDialogItem
import com.pandacorp.randomui.presentation.utils.helpers.Constants

class SettingsDialog(private val activity: Activity, private val preferenceKey: String) : CustomDialog(activity) {
    private lateinit var binding: DialogListViewBinding

    private fun fillThemesList(): MutableList<SettingsDialogItem> {
        val keysList = activity.resources.getStringArray(R.array.Themes_values)
        val titlesList = activity.resources.getStringArray(R.array.Themes)
        val itemsList =
            activity.resources.obtainTypedArray(R.array.Themes_drawables)
        val themesList: MutableList<SettingsDialogItem> = mutableListOf()
        repeat(keysList.size) { i ->
            themesList.add(
                SettingsDialogItem(
                    keysList[i],
                    titlesList[i],
                    itemsList.getDrawable(i)!!,
                ),
            )
        }
        itemsList.recycle()
        return themesList
    }

    private fun fillLanguagesList(): MutableList<SettingsDialogItem> {
        val keysList = activity.resources.getStringArray(R.array.Languages_values)
        val drawablesList =
            activity.resources.obtainTypedArray(R.array.Languages_drawables)
        val titlesList = activity.resources.getStringArray(R.array.Languages)
        val itemsList: MutableList<SettingsDialogItem> = mutableListOf()
        repeat(keysList.size) { i ->
            itemsList.add(
                SettingsDialogItem(
                    keysList[i],
                    titlesList[i],
                    drawablesList.getDrawable(i)!!,
                ),
            )
        }
        drawablesList.recycle()
        return itemsList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogListViewBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.dialogListViewTitle.setText(
            when (preferenceKey) {
                Constants.PreferenceKeys.themesKey -> R.string.theme
                Constants.PreferenceKeys.languagesKey -> R.string.language
                else -> throw IllegalArgumentException("PreferenceKey = $preferenceKey")
            },
        )

        binding.dialogListViewCancel.setOnClickListener {
            cancel()
        }

        val itemsList: MutableList<SettingsDialogItem> = when (preferenceKey) {
            Constants.PreferenceKeys.themesKey -> fillThemesList()
            Constants.PreferenceKeys.languagesKey -> fillLanguagesList()
            else -> throw IllegalArgumentException()
        }
        val adapter = SettingsAdapter(activity, itemsList, preferenceKey)
        adapter.setOnClickListener { listItem ->
            sp.edit().putString(preferenceKey, listItem.value).apply()
            cancel()
            activity.apply {
                recreate()
            }
        }
        binding.dialogListViewListView.adapter = adapter
    }
}