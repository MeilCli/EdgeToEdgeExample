package net.meilcli.etee.sticky

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_sticky.*
import net.meilcli.etee.R
import net.meilcli.etee.extensions.setTranslucentStatusAndNavigationBarLayout

class StickyActivity : AppCompatActivity() {

    private val defaultScrollViewPaddingTop by lazy { scrollView.paddingTop }
    private var scrollViewPaddingTopByTabLayout = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sticky)

        setTranslucentStatusAndNavigationBarLayout(
            ContextCompat.getColor(this, R.color.translucent),
            ContextCompat.getColor(this, R.color.translucent)
        )
        fitsStatusBar()
        fitsNavigationBar()
        fitsTabLayout()
    }


    private fun fitsStatusBar() {
        ViewCompat.setOnApplyWindowInsetsListener(statusBarMargin) { _, inset ->
            statusBarMargin.layoutParams = statusBarMargin.layoutParams
                .apply {
                    height = inset.systemWindowInsetTop
                }
            return@setOnApplyWindowInsetsListener inset
        }

        ViewCompat.setOnApplyWindowInsetsListener(tabLayout) { _, inset ->
            tabLayout.layoutParams = tabLayout.layoutParams
                .let { it as? ViewGroup.MarginLayoutParams }
                ?.apply {
                    topMargin = inset.systemWindowInsetTop
                }
            return@setOnApplyWindowInsetsListener inset
        }
    }

    private fun fitsNavigationBar() {
        val defaultScrollViewPaddingBottom = scrollView.paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(scrollView) { _, inset ->
            scrollView.setPadding(
                scrollView.paddingLeft,
                scrollView.paddingTop,
                scrollView.paddingRight,
                defaultScrollViewPaddingBottom + inset.systemWindowInsetBottom
            )
            return@setOnApplyWindowInsetsListener inset
        }
    }

    private fun fitsTabLayout() {
        tabLayout.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            scrollViewPaddingTopByTabLayout = tabLayout.height
            setScrollViewPadding()
        }
    }

    private fun setScrollViewPadding() {
        val paddingTop = defaultScrollViewPaddingTop + scrollViewPaddingTopByTabLayout
        scrollView.setPadding(
            scrollView.paddingLeft,
            paddingTop,
            scrollView.paddingRight,
            scrollView.paddingBottom
        )
    }
}