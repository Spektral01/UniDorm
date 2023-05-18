package com.example.unidorm.fragments.contract

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun showNotificationScreen()

    fun showInfoScreen()
    fun showShopScreen()

    fun showAccountScreen()
    fun showCreateNotificationScreen()
    fun showSearchItemDrawer()
/*
    fun showCabinetScreen()

    fun showShopItemInfoScreen()

    fun showShopItemSellScreen()

    fun showInfoFullSelectedScreen()


    fun <T : Parcelable> publishResult(result: T)

    fun <T : Parcelable> listenResult(clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>)*/

}