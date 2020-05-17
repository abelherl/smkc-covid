package com.example.smkccovid.behavior

import android.view.View
import androidx.viewpager.widget.ViewPager


class ZoomOutTransition : ViewPager.PageTransformer {

        companion object {
            private const val MIN_SCALE = 0.6f
            private const val MIN_ALPHA = 0.2f
        }

    override fun transformPage(page: View, position: Float) {
        if (position < -1) {  // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(0F)
        } else if (position <= 1) { // [-1,1]
            page.setScaleX(
                Math.max(
                    MIN_SCALE,
                    1 - Math.abs(position)
                )
            )
            page.setScaleY(
                Math.max(
                    MIN_SCALE,
                    1 - Math.abs(position)
                )
            )
            page.setAlpha(
                Math.max(
                    MIN_ALPHA,
                    1 - Math.abs(position)
                )
            )
        } else {  // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0F)
        }
    }
}
