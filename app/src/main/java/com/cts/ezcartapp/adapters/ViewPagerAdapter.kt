package com.cts.ezcartapp.adapters

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.cts.ezcartapp.R

class ViewPagerAdapter: PagerAdapter() {

    override fun instantiateItem(
        collection: ViewGroup,
        position: Int
    ): Any {
        var resId = 0
        when (position) {
            0 -> resId = R.id.imageOne
            1 -> resId = R.id.imageTwo
            2 -> resId = R.id.imageThree
           }
        return collection.findViewById(resId)
    }

    override fun isViewFromObject(
        arg0: View,
        arg1: Any
    ) = arg0 === arg1 as View

    override fun getCount() = 3


    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        arg1: Any
    ) = Unit
}