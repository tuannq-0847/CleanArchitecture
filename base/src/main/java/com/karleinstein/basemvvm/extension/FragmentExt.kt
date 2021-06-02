package com.karleinstein.basemvvm.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.addFragment(
    fragment: Fragment,
    container: Int,
    isAddToBackStack: Boolean = false
) {
    val fragmentTransaction = beginTransaction().add(container, fragment)
    if (isAddToBackStack) fragmentTransaction.addToBackStack(fragment.javaClass.simpleName).commit()
    else fragmentTransaction.commit()
}

fun FragmentManager.replaceFragment(
    fragment: Fragment,
    container: Int,
    isAddToBackStack: Boolean = false
) {
    val fragmentTransaction = beginTransaction().replace(container, fragment)
    if (isAddToBackStack) fragmentTransaction.addToBackStack(fragment.javaClass.simpleName).commit()
    else fragmentTransaction.commit()
}
