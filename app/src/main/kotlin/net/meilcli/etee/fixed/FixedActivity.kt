package net.meilcli.etee.fixed

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_fixed.*
import net.meilcli.etee.R
import net.meilcli.etee.extensions.setTranslucentStatusAndNavigationBarLayout

class FixedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fixed)

        setTranslucentStatusAndNavigationBarLayout(
            ContextCompat.getColor(this, R.color.translucent),
            ContextCompat.getColor(this, R.color.translucent)
        )
        fitsStatusBar()
        fitsNavigationBar()
    }


    private fun fitsStatusBar() {
        ViewCompat.setOnApplyWindowInsetsListener(statusBarMargin) { _, inset ->
            statusBarMargin.layoutParams = statusBarMargin.layoutParams
                .apply {
                    height = inset.systemWindowInsetTop
                }

            return@setOnApplyWindowInsetsListener inset
        }
        ViewCompat.setOnApplyWindowInsetsListener(header) { _, inset ->
            header.layoutParams = header.layoutParams
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
}