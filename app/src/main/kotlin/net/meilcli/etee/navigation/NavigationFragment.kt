package net.meilcli.etee.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_navigation.*
import net.meilcli.etee.R

class NavigationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fitsNavigationBar()
    }

    private fun fitsNavigationBar() {
        val defaultScrollViewPaddingBottom = scrollView.paddingBottom
        ViewCompat.setOnApplyWindowInsetsListener(scrollView) { _, insets ->
            /*scrollView.updateLayoutParams<FrameLayout.LayoutParams> {
                bottomMargin = -insets.systemWindowInsetBottom
            }*/
            scrollView.updatePadding(bottom = defaultScrollViewPaddingBottom + insets.systemWindowInsetBottom)
            return@setOnApplyWindowInsetsListener insets
        }
    }
}