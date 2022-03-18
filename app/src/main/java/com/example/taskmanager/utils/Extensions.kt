package com.example.taskmanager.utils

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.utils.SettingsPrefsConstants.SETTING_PREFERENCE_NAME

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTING_PREFERENCE_NAME)

fun Activity.statusBarColor(color : Int) {
    window.statusBarColor = color
}

fun Fragment.statusBarColor(color: Int) {
    requireActivity().statusBarColor(color)
}

fun Fragment.navigateUp() {
    findNavController().navigateUp()
}

fun Fragment.safeNavigate(resId: Int, args: Bundle? = null) {
    findNavController().currentDestination?.getAction(resId)?.run {
        findNavController().navigate(resId, args)
    }
}

fun Fragment.showToast(msg : String, long : Boolean = false) {
    Toast.makeText(requireContext(), msg, if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(@StringRes msg : Int, long : Boolean = false) {
    Toast.makeText(requireContext(), getString(msg), if (long) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}
