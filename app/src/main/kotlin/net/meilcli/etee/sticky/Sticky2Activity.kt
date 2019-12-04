package net.meilcli.etee.sticky

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import kotlinx.android.synthetic.main.activity_sticky2.*
import net.meilcli.etee.R
import net.meilcli.etee.extensions.setTranslucentStatusAndNavigationBarLayout

class Sticky2Activity : AppCompatActivity() {

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
            statusBarMargin.updateLayoutParams {
                height = inset.systemWindowInsetTop
            }
            return@setOnApplyWindowInsetsListener inset
        }
    }

    private fun fitsNavigationBar() {
        val defaultScrollViewPaddingBottom = scrollView.paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(scrollView) { _, inset ->
            scrollView.updatePadding(bottom = defaultScrollViewPaddingBottom + inset.systemWindowInsetBottom)
            return@setOnApplyWindowInsetsListener inset
        }
    }

    private fun fitsTabLayout() {
        ViewCompat.setOnApplyWindowInsetsListener(tabLayout) { _, inset ->
            tabLayout.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = inset.systemWindowInsetTop
                rightMargin = inset.systemWindowInsetRight
            }
            return@setOnApplyWindowInsetsListener inset
        }
        tabLayout.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            scrollViewPaddingTopByTabLayout = tabLayout.height
            setScrollViewPadding()
        }
    }

    private fun setScrollViewPadding() {
        scrollView.updatePadding(top = defaultScrollViewPaddingTop + scrollViewPaddingTopByTabLayout)
    }
}