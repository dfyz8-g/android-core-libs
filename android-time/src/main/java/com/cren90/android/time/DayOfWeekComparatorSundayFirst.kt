/**
 * Created by Chris Renfrow on 4/8/18.
 */

package com.cren90.android.time

object DayOfWeekComparatorSundayFirst : Comparator<WeekDay> {

    override fun compare(o1: WeekDay, o2: WeekDay): Int =
        when (o1) {
            WeekDay.SUNDAY -> {
                when (o2) {
                    WeekDay.SUNDAY    -> 0
                    WeekDay.MONDAY    -> -1
                    WeekDay.TUESDAY   -> -2
                    WeekDay.WEDNESDAY -> -3
                    WeekDay.THURSDAY  -> -4
                    WeekDay.FRIDAY    -> -5
                    else              -> -6
                }
            }
            WeekDay.MONDAY -> {
                when (o2) {
                    WeekDay.SUNDAY    -> 1
                    WeekDay.MONDAY    -> 0
                    WeekDay.TUESDAY   -> -1
                    WeekDay.WEDNESDAY -> -2
                    WeekDay.THURSDAY  -> -3
                    WeekDay.FRIDAY    -> -4
                    else              -> -5
                }
            }
            WeekDay.TUESDAY -> {
                when (o2) {
                    WeekDay.SUNDAY    -> 2
                    WeekDay.MONDAY    -> 1
                    WeekDay.TUESDAY   -> 0
                    WeekDay.WEDNESDAY -> -1
                    WeekDay.THURSDAY  -> -2
                    WeekDay.FRIDAY    -> -3
                    else              -> -4
                }
            }
            WeekDay.WEDNESDAY -> {
                when (o2) {
                    WeekDay.SUNDAY    -> 3
                    WeekDay.MONDAY    -> 2
                    WeekDay.TUESDAY   -> 1
                    WeekDay.WEDNESDAY -> 0
                    WeekDay.THURSDAY  -> -1
                    WeekDay.FRIDAY    -> -2
                    else              -> -3
                }
            }
            WeekDay.THURSDAY -> {
                when (o2) {
                    WeekDay.SUNDAY    -> 4
                    WeekDay.MONDAY    -> 3
                    WeekDay.TUESDAY   -> 2
                    WeekDay.WEDNESDAY -> 1
                    WeekDay.THURSDAY  -> 0
                    WeekDay.FRIDAY    -> -1
                    else              -> -2
                }
            }
            WeekDay.FRIDAY -> {
                when (o2) {
                    WeekDay.SUNDAY    -> 5
                    WeekDay.MONDAY    -> 4
                    WeekDay.TUESDAY   -> 3
                    WeekDay.WEDNESDAY -> 2
                    WeekDay.THURSDAY  -> 1
                    WeekDay.FRIDAY    -> 0
                    else              -> -1
                }
            }
            else              -> {
                when (o2) {
                    WeekDay.SUNDAY -> 6
                    WeekDay.MONDAY -> 5
                    WeekDay.TUESDAY -> 4
                    WeekDay.WEDNESDAY -> 3
                    WeekDay.THURSDAY -> 2
                    WeekDay.FRIDAY -> 1
                    else              -> 0
                }
            }
        }
}