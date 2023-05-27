package com.pandacorp.randomui.presentation.utils.dialogs

import android.app.Activity
import android.os.Bundle
import com.pandacorp.randomui.R
import com.pandacorp.randomui.databinding.DialogListViewBinding
import com.pandacorp.randomui.presentation.ui.adapter.ListAdapter
import com.pandacorp.randomui.presentation.ui.adapter.ListItem
import com.pandacorp.randomui.presentation.utils.Constants

class DialogListView(private val activity: Activity, private val preferenceKey: String) : CustomDialog(activity) {
    private lateinit var binding: DialogListViewBinding

    private fun fillThemesList(): MutableList<ListItem> {
        val keysList = activity.resources.getStringArray(R.array.Themes_values)
        val titlesList = activity.resources.getStringArray(R.array.Themes)
        val itemsList =
            activity.resources.obtainTypedArray(R.array.Themes_drawables)
        val themesList: MutableList<ListItem> = mutableListOf()
        repeat(keysList.size) { i ->
            themesList.add(
                ListItem(
                    keysList[i],
                    titlesList[i],
                    itemsList.getDrawable(i)!!
                )
            )
        }
        itemsList.recycle()
        return themesList
    }

    private fun fillLanguagesList(): MutableList<ListItem> {
        val keysList = activity.resources.getStringArray(R.array.Languages_values)
        val drawablesList =
            activity.resources.obtainTypedArray(R.array.Languages_drawables)
        val titlesList = activity.resources.getStringArray(R.array.Languages)
        val itemsList: MutableList<ListItem> = mutableListOf()
        repeat(keysList.size) { i ->
            itemsList.add(
                ListItem(
                    keysList[i],
                    titlesList[i],
                    drawablesList.getDrawable(i)!!
                )
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
            }
        )

        binding.dialogListViewCancel.setOnClickListener {
            cancel()
        }

        val itemsList: MutableList<ListItem> = when (preferenceKey) {
            Constants.PreferenceKeys.themesKey -> fillThemesList()
            Constants.PreferenceKeys.languagesKey -> fillLanguagesList()
            else -> throw IllegalArgumentException()

        }
        val adapter = ListAdapter(activity, itemsList, preferenceKey)
        adapter.setOnClickListener { listItem ->
            sp.edit().putString(preferenceKey, listItem.value).apply()
            cancel()
//            requireActivity().setResult(AppCompatActivity.RESULT_OK)
//            requireActivity().startActivity(Intent(context, SettingsActivity::class.java))
//            requireActivity().finish() todo:
            activity.apply {
                recreate()
                overridePendingTransition(0, 0)
            }
        }
        binding.dialogListViewListView.adapter = adapter
    }
}