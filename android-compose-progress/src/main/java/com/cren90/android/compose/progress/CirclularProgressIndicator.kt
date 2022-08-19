package com.cren90.android.compose.progress

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.max

/**
 * Determinate <a href="https://material.io/components/progress-indicators#circular-progress-indicators" class="external" target="_blank">Material Design circular progress indicator</a>.
 *
 * Progress indicators express an unspecified wait time or display the length of a process.
 *
 * ![Circular progress indicator image](https://developer.android.com/images/reference/androidx/compose/material/circular-progress-indicator.png)
 *
 * By default there is no animation between [progress] values. You can use
 * [ProgressIndicatorDefaults.progressAnimationSpec] as the default recommended
 * [AnimationSpec] when animating progress, such as in the following example:
 *
 * @param progress The progress of this progress indicator, where 0.0 represents no progress and 1.0
 * represents full progress. Values outside of this range are coerced into the range.
 * @param color The color of the progress indicator.
 * @param strokeWidth The stroke width for the progress indicator.
 */
@Composable
fun CircularProgressIndicator(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    cap: StrokeCap = StrokeCap.Butt,
    strokeWidth: Dp = ProgressIndicatorDefaults.defaultStrokeWidth,
    diameter: Dp = defaultCircularIndicatorDiameter
) {
    CircularProgressIndicator(
        progress,
        modifier,
        Brush.linearGradient(listOf(color)),
        cap,
        strokeWidth,
        diameter
    )
}


/**
 * Determinate <a href="https://material.io/components/progress-indicators#circular-progress-indicators" class="external" target="_blank">Material Design circular progress indicator</a>.
 *
 * Progress indicators express an unspecified wait time or display the length of a process.
 *
 * ![Circular progress indicator image](https://developer.android.com/images/reference/androidx/compose/material/circular-progress-indicator.png)
 *
 * By default there is no animation between [progress] values. You can use
 * [ProgressIndicatorDefaults.progressAnimationSpec] as the default recommended
 * [AnimationSpec] when animating progress, such as in the following example:
 *
 * @param progress The progress of this progress indicator, where 0.0 represents no progress and 1.0
 * represents full progress. Values outside of this range are coerced into the range.
 * @param brush The brush of the progress indicator.
 * @param strokeWidth The stroke width for the progress indicator.
 */
@Composable
fun CircularProgressIndicator(
    /*@FloatRange(from = 0.0, to = 1.0)*/
    progress: Float,
    modifier: Modifier = Modifier,
    brush: Brush = Brush.linearGradient(listOf(MaterialTheme.colors.primary)),
    cap: StrokeCap = StrokeCap.Butt,
    strokeWidth: Dp = ProgressIndicatorDefaults.defaultStrokeWidth,
    diameter: Dp = defaultCircularIndicatorDiameter
) {
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = cap)
    }
    Canvas(
        modifier
            .progressSemantics(progress)
            .size(diameter)
    ) {
        // Start at 12 O'clock
        val startAngle = 270f
        val sweep = progress * 360f
        drawDeterminateCircularIndicator(startAngle, sweep, brush, stroke)
    }
}

/**
 * Indeterminate <a href="https://material.io/components/progress-indicators#circular-progress-indicators" class="external" target="_blank">Material Design circular progress indicator</a>.
 *
 * Progress indicators express an unspecified wait time or display the length of a process.
 *
 * ![Circular progress indicator image](https://developer.android.com/images/reference/androidx/compose/material/circular-progress-indicator.png)
 *
 * @param color The color of the progress indicator.
 * @param strokeWidth The stroke width for the progress indicator.
 */
@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    cap: StrokeCap = StrokeCap.Butt,
    strokeWidth: Dp = ProgressIndicatorDefaults.defaultStrokeWidth,
    diameter: Dp = defaultCircularIndicatorDiameter
) {
    CircularProgressIndicator(
        modifier,
        Brush.linearGradient(listOf(color)),
        cap,
        strokeWidth,
        diameter
    )
}

/**
 * Indeterminate <a href="https://material.io/components/progress-indicators#circular-progress-indicators" class="external" target="_blank">Material Design circular progress indicator</a>.
 *
 * Progress indicators express an unspecified wait time or display the length of a process.
 *
 * ![Circular progress indicator image](https://developer.android.com/images/reference/androidx/compose/material/circular-progress-indicator.png)
 *
 * @param brush The brush of the progress indicator.
 * @param strokeWidth The stroke width for the progress indicator.
 */
@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    brush: Brush = Brush.linearGradient(listOf(MaterialTheme.colors.primary)),
    cap: StrokeCap = StrokeCap.Butt,
    strokeWidth: Dp = ProgressIndicatorDefaults.defaultStrokeWidth,
    diameter: Dp = defaultCircularIndicatorDiameter
) {
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = cap)
    }

    val transition = rememberInfiniteTransition()
    // The current rotation around the circle, so we know where to start the rotation from
    val currentRotation by transition.animateValue(
        0,
        ROTATIONS_PER_CYCLE,
        Int.VectorConverter,
        infiniteRepeatable(
            animation = tween(
                durationMillis = ROATION_DURATION * ROTATIONS_PER_CYCLE,
                easing = LinearEasing
            )
        )
    )
    // How far forward (degrees) the base point should be from the start point
    val baseRotation by transition.animateFloat(
        0f,
        BASE_ROTATION_ANGLE,
        infiniteRepeatable(
            animation = tween(
                durationMillis = ROATION_DURATION,
                easing = LinearEasing
            )
        )
    )
    // How far forward (degrees) both the head and tail should be from the base point
    val endAngle by transition.animateFloat(
        0f,
        JUMP_ROTATION_ANGLE,
        infiniteRepeatable(
            animation = keyframes {
                durationMillis = HEAD_AND_TAIL_ANIMATION_DURATION + HEAD_AND_TAIL_DELAY_DURATION
                0f at 0 with circularEasing
                JUMP_ROTATION_ANGLE at HEAD_AND_TAIL_ANIMATION_DURATION
            }
        )
    )

    val startAngle by transition.animateFloat(
        0f,
        JUMP_ROTATION_ANGLE,
        infiniteRepeatable(
            animation = keyframes {
                durationMillis = HEAD_AND_TAIL_ANIMATION_DURATION + HEAD_AND_TAIL_DELAY_DURATION
                0f at HEAD_AND_TAIL_DELAY_DURATION with circularEasing
                JUMP_ROTATION_ANGLE at durationMillis
            }
        )
    )
    Canvas(
        modifier
            .progressSemantics()
            .size(diameter)
    ) {

        val currentRotationAngleOffset = (currentRotation * ROTATION_ANGLE_OFFSET) % 360f

        // How long a line to draw using the start angle as a reference point
        val sweep = abs(endAngle - startAngle)

        // Offset by the constant offset and the per rotation offset
        val offset = START_ANGLE_OFFSET + currentRotationAngleOffset + baseRotation
        drawIndeterminateCircularIndicator(startAngle + offset, strokeWidth, sweep, brush, stroke)
    }
}

private fun DrawScope.drawCircularIndicator(
    startAngle: Float,
    sweep: Float,
    brush: Brush,
    stroke: Stroke
) {
    // To draw this circle we need a rect with edges that line up with the midpoint of the stroke.
    // To do this we need to remove half the stroke width from the total diameter for both sides.
    val diameterOffset = stroke.width / 2
    val arcDimen = size.width - 2 * diameterOffset
    drawArc(
        brush = brush,
        startAngle = startAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(diameterOffset, diameterOffset),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
}

/**
 * Contains the default values used for [LinearProgressIndicator] and [CircularProgressIndicator].
 */
object ProgressIndicatorDefaults {
    /**
     * Default stroke width for [CircularProgressIndicator], and default height for
     * [LinearProgressIndicator].
     *
     * This can be customized with the `strokeWidth` parameter on [CircularProgressIndicator],
     * and by passing a layout modifier setting the height for [LinearProgressIndicator].
     */
    val defaultStrokeWidth = 4.dp

    /**
     * The default [AnimationSpec] that should be used when animating between progress in a
     * determinate progress indicator.
     */
    val progressAnimationSpec = SpringSpec(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessVeryLow,
        // The default threshold is 0.01, or 1% of the overall progress range, which is quite
        // large and noticeable.
        visibilityThreshold = 1 / 1000f
    )
}

private fun DrawScope.drawDeterminateCircularIndicator(
    startAngle: Float,
    sweep: Float,
    brush: Brush,
    stroke: Stroke
) = drawCircularIndicator(startAngle, sweep, brush, stroke)

private fun DrawScope.drawIndeterminateCircularIndicator(
    startAngle: Float,
    strokeWidth: Dp,
    sweep: Float,
    brush: Brush,
    stroke: Stroke
) {
    // Length of arc is angle * radius
    // Angle (radians) is length / radius
    // The length should be the same as the stroke width for calculating the min angle
    val squareStrokeCapOffset =
        (180.0 / PI).toFloat() * (strokeWidth / (defaultCircularIndicatorDiameter / 2)) / 2f

    // Adding a square stroke cap draws half the stroke width behind the start point, so we want to
    // move it forward by that amount so the arc visually appears in the correct place
    val adjustedStartAngle = startAngle + squareStrokeCapOffset

    // When the start and end angles are in the same place, we still want to draw a small sweep, so
    // the stroke caps get added on both ends and we draw the correct minimum length arc
    val adjustedSweep = max(sweep, 0.1f)

    drawCircularIndicator(adjustedStartAngle, adjustedSweep, brush, stroke)
}

// CircularProgressIndicator Material specs
// Diameter of the indicator circle
private val defaultCircularIndicatorDiameter = 40.dp

// Indeterminate circular indicator transition specs

// The animation comprises of 5 rotations around the circle forming a 5 pointed star.
// After the 5th rotation, we are back at the beginning of the circle.
private const val ROTATIONS_PER_CYCLE = 5

// Each rotation is 1 and 1/3 seconds, but 1332ms divides more evenly
private const val ROATION_DURATION = 1332

// When the rotation is at its beginning (0 or 360 degrees) we want it to be drawn at 12 o clock,
// which means 270 degrees when drawing.
private const val START_ANGLE_OFFSET = -90f

// How far the base point moves around the circle
private const val BASE_ROTATION_ANGLE = 286f

// How far the head and tail should jump forward during one rotation past the base point
private const val JUMP_ROTATION_ANGLE = 290f

// Each rotation we want to offset the start position by this much, so we continue where
// the previous rotation ended. This is the maximum angle covered during one rotation.
private const val ROTATION_ANGLE_OFFSET = (BASE_ROTATION_ANGLE + JUMP_ROTATION_ANGLE) % 360f

// The head animates for the first half of a rotation, then is static for the second half
// The tail is static for the first half and then animates for the second half
private const val HEAD_AND_TAIL_ANIMATION_DURATION = (ROATION_DURATION * 0.5).toInt()
private const val HEAD_AND_TAIL_DELAY_DURATION = HEAD_AND_TAIL_ANIMATION_DURATION

// The easing for the head and tail jump
private val circularEasing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)