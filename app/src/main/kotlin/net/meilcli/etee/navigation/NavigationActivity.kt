package net.meilcli.etee.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_navigation.*
import net.meilcli.etee.R
import net.meilcli.etee.extensions.setTranslucentStatusAndNavigationBarLayout

class NavigationActivity : AppCompatActivity() {

    companion object {

        private const val tab1 = "tab1"
        private const val tab2 = "tab2"
        private const val tab3 = "tab3"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        setTranslucentStatusAndNavigationBarLayout(
            ContextCompat.getColor(
                this,
                R.color.translucent
            ), ContextCompat.getColor(this, R.color.translucent)
        )
        fitsStatusBar()
        fitsNavigationBar()

        setupBottomNavigation()
    }

    private fun fitsStatusBar() {
        ViewCompat.setOnApplyWindowInsetsListener(statusBarSpace) { _, insets ->
            statusBarSpace.updateLayoutParams {
                height = insets.systemWindowInsetTop
            }
            return@setOnApplyWindowInsetsListener insets
        }
    }

    private fun fitsNavigationBar() {
        ViewCompat.setOnApplyWindowInsetsListener(bottomNavigation) { _, insets ->
            bottomNavigation.updatePadding(bottom = bottomNavigation.paddingBottom + insets.systemWindowInsetBottom)
            return@setOnApplyWindowInsetsListener insets
        }
    }

    private fun setupBottomNavigation() {
        val tab1Fragment = getFragmentOrCreate(tab1, hide = false) { NavigationFragment() }
        val tab2Fragment = getFragmentOrCreate(tab2, hide = true) { NavigationFragment() }
        val tab3Fragment = getFragmentOrCreate(tab3, hide = true) { NavigationFragment() }

        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.tab1 -> {
                    supportFragmentManager.beginTransaction()
                        .show(tab1Fragment)
                        .hide(tab2Fragment)
                        .hide(tab3Fragment)
                        .commit()
                }
                R.id.tab2 -> {
                    supportFragmentManager.beginTransaction()
                        .hide(tab1Fragment)
                        .show(tab2Fragment)
                        .hide(tab3Fragment)
                        .commit()
                }
                R.id.tab3 -> {
                    supportFragmentManager.beginTransaction()
                        .hide(tab1Fragment)
                        .hide(tab2Fragment)
                        .show(tab3Fragment)
                        .commit()
                }
            }
            true
        }
    }

    private inline fun getFragmentOrCreate(
        tag: String,
        hide: Boolean,
        creator: () -> Fragment
    ): Fragment {
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            if (hide) {
                supportFragmentManager.beginTransaction()
                    .hide(fragment)
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .show(fragment)
                    .commit()
            }
            return fragment
        }
        fragment = creator()
        if (hide) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment, tag)
                .hide(fragment)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment, tag)
                .commit()
        }
        return fragment
    }
}