package com.cren90.android.common.providers.resources

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

interface LayoutManagerProvider {
    /**
     * Returns a [LinearLayoutManager] for the given [orientation]
     */
    fun getLinearLayoutManager(orientation: Int, reverseLayout: Boolean): LinearLayoutManager

    /**
     * Returns a [GridLayoutManager] with the given [numberColumns]
     */
    fun getGridLayoutManager(numberColumns: Int): GridLayoutManager

}