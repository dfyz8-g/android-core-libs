@file:Suppress("unused")

package com.cren90.android.common.providers.resources

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Spanned
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cren90.android.common.extensions.getHtmlSpannedString
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class AndroidResourceProvider @Inject constructor(val context: Context) :
    ColorProvider,
    DrawableProvider,
    IntegerProvider,
    DimenProvider,
    LayoutManagerProvider,
    StringProvider,
    BooleanProvider {

    //-------------------------------------------
    // region ColorProvider Overrides
    //-------------------------------------------

    override fun getColor(@ColorRes resourceID: Int): Int {
        return ContextCompat.getColor(context, resourceID)
    }

    override fun getColor(resourceID: Int, alpha: Float): Int {
        val color = getColor(resourceID)

        return adjustAlpha(color, alpha)
    }

    override fun getColor(
        @ColorRes lowColorResourceID: Int, @ColorRes
        highColorResourceID: Int, proportion: Float
    ): Int {
        return ArgbEvaluator().evaluate(
            proportion,
            getColor(lowColorResourceID),
            getColor(highColorResourceID)
        ) as Int
    }

    private fun adjustAlpha(
        color: Int,
        alpha: Float
    ) = Color.argb(
        (Color.alpha(color) * alpha).roundToInt(),
        Color.red(color),
        Color.green(color),
        Color.blue(color)
    )

    //-------------------------------------------
    // endregion ColorProvider Overrides
    //-------------------------------------------

    //-------------------------------------------
    // region DrawableProvider Overrides
    //-------------------------------------------

    @SuppressLint("ObsoleteSdkInt")
    override fun getDrawable(@DrawableRes resourceID: Int?): Drawable? {
        return resourceID?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.resources?.getDrawable(it, context.theme)
            } else {
                @Suppress("DEPRECATION")
                context.resources?.getDrawable(resourceID)
            }
        }
    }

    //-------------------------------------------
    // endregion DrawableProvider Overrides
    //-------------------------------------------

    //-------------------------------------------
    // region IntegerProvider Overrides
    //-------------------------------------------

    override fun getInt(@IntegerRes resourceID: Int) = context.resources.getInteger(resourceID)

    //-------------------------------------------
    // endregion IntegerProvider Overrides
    //-------------------------------------------

    //----------------------------------------------------------------------------------------------
    // region DimenProvider Overrides
    //----------------------------------------------------------------------------------------------

    override fun getDimen(@DimenRes resourceID: Int) = context.resources.getDimension(resourceID)

    //----------------------------------------------------------------------------------------------
    // endregion DimenProvider Overrides
    //----------------------------------------------------------------------------------------------

    //-------------------------------------------
    // region LayoutManagerProvider Overrides
    //-------------------------------------------

    override fun getLinearLayoutManager(orientation: Int, reverseLayout: Boolean) = object :
        LinearLayoutManager(context, orientation, reverseLayout) {
        override fun supportsPredictiveItemAnimations(): Boolean {
            return true
        }
    }

    override fun getGridLayoutManager(numberColumns: Int): GridLayoutManager = object :
        GridLayoutManager(context, numberColumns) {
        override fun supportsPredictiveItemAnimations(): Boolean {
            return true
        }
    }

    //-------------------------------------------
    // endregion LayoutManagerProvider Overrides
    //-------------------------------------------

    //-------------------------------------------
    // region StringProvider Overrides
    //-------------------------------------------

    override fun getString(resourceID: Int): String = context.getString(resourceID)

    override fun getString(resourceID: Int, vararg formatArgs: Any): String = context.getString(
        resourceID,
        *formatArgs
    )

    override fun getHtmlString(resourceID: Int): Spanned = context.getHtmlSpannedString(resourceID)

    override fun getHtmlString(
        resourceID: Int,
        vararg formatArgs: Any
    ): Spanned = context.getHtmlSpannedString(
        resourceID,
        *formatArgs
    )

    override fun getPluralString(
        resourceID: Int,
        count: Int
    ): String = context.resources.getQuantityString(
        resourceID,
        count
    )

    override fun getPluralString(
        resourceID: Int,
        count: Int,
        vararg formatArgs: Any
    ): String = context.resources.getQuantityString(
        resourceID,
        count,
        *formatArgs
    )

    //-------------------------------------------
    // endregion StringProvider Overrides
    //-------------------------------------------

    override fun getBoolean(resourceID: Int): Boolean = context.resources.getBoolean(resourceID)

}