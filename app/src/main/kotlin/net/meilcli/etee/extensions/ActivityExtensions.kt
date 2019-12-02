package net.meilcli.etee.extensions

import android.app.Activity
import androidx.annotation.ColorInt
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN as systemUiFlagLayoutFullScreen
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION as systemUiFlagLayoutHideNavigation
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE as systemUiFlagLayoutStable

fun Activity.setTranslucentStatusBarLayout(@ColorInt statusBarColor: Int) {
    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
            systemUiFlagLayoutStable or
            systemUiFlagLayoutFullScreen

    window.statusBarColor = statusBarColor
}

fun Activity.setTranslucentStatusAndNavigationBarLayout(
    @ColorInt statusBarColor: Int,
    @ColorInt navigationBarColor: Int
) {
    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
            systemUiFlagLayoutStable or
            systemUiFlagLayoutFullScreen or
            systemUiFlagLayoutHideNavigation

    window.statusBarColor = statusBarColor
    window.navigationBarColor = navigationBarColor
}