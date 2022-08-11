/**
 * Created by Chris Renfrow on 4/16/18.
 */

@file:Suppress("unused")

package com.cren90.android.time

import com.cren90.android.common.providers.resources.StringProvider
import com.cren90.kotlin.common.extensions.replaceLast

fun getDaysString(days: List<WeekDay>, stringProvider: StringProvider): String {

    /* Always display the days of the week in the occurrence subtext in chronological order (sun-sat)
     * if every day of the week is selected, display as "Everyday"
     * if all week days (mon-fri) are selected, display as "Weekdays"
     * if all weekend days (sat and sun) are selected, display as "Weekends"
     * if 3 or more consecutive days are selected, display as "[first day selected] - [last day selected]"
     ** if 3 or more consecutive days are selected with other days also selected, display as "[first day selected] - [last day selected], [day], & [day]".
     ** Example: if the user selects Sat, Sun, Mon, Thurs, Fri, display "Sat-Mon, Thurs, & Fri"
     */

    if (days.size == 1) {
        return mapDayToString(days.first(), stringProvider)
    } else if (days.isEmpty()) {
        return ""
    }

    val weekdayCount = 5
    var weekdayCounter = 0
    val weekendCount = 2
    var weekendCounter = 0

    days.forEach {
        when (it) {
            WeekDay.MONDAY, WeekDay.TUESDAY, WeekDay.WEDNESDAY, WeekDay.THURSDAY, WeekDay.FRIDAY -> weekdayCounter++
            WeekDay.SATURDAY, WeekDay.SUNDAY -> weekendCounter++
        }
    }

    if (weekdayCount == weekdayCounter && weekendCount == weekendCounter) {
        return stringProvider.getString(R.string.everyday)
    } else if (weekdayCount == weekdayCounter && weekendCounter == 0) {
        return stringProvider.getString(R.string.weekdays)
    } else if (weekendCount == weekendCounter && weekdayCounter == 0) {
        return stringProvider.getString(R.string.weekends)
    }

    val sortedDays = days.asSequence().distinct().sortedWith(DayOfWeekComparatorSundayFirst)
        .toList()

    val firstConsecPair =
        separateFirstSetOfConsecutiveDays(sortedDays)

    val consecutives = firstConsecPair.first
    val nonconsecutives = firstConsecPair.second

    var daysString = ""

    if (consecutives.isNotEmpty() && consecutives.count() > 1) {
        daysString =
            "${
                mapDayToString(
                    consecutives.first(),
                    stringProvider
                )
            } - ${mapDayToString(consecutives.last(), stringProvider)}"
    }

    daysString = if (daysString.isEmpty()) "" else "$daysString, "

    daysString =
        "$daysString${
            nonconsecutives.joinToString(", ") {
                mapDayToString(
                    it,
                    stringProvider
                )
            }
        }".trim(' ', ',')

    if (daysString.split(",").size > 2) {
        daysString = daysString.replaceLast(",", ", &")
    }

    return daysString
}

fun getSimplifiedDaysString(days: List<WeekDay>, separator: String = " ", stringProvider: StringProvider): String {

    if (days.size == 1) {
        return mapDayToString(days.first(), stringProvider)
    } else if (days.isEmpty()) {
        return ""
    }

    val sortedDays = days.asSequence().distinct().sortedWith(DayOfWeekComparatorSundayFirst).map {
        mapDayToString(it, stringProvider)
    }
        .toList()

    val dayString = sortedDays.joinToString(separator)

    return dayString.trim(' ', ',')
}

fun mapDayToString(day: WeekDay, stringProvider: StringProvider): String {

    return when (day) {
        WeekDay.SUNDAY -> stringProvider.getString(R.string.sunday)
        WeekDay.MONDAY -> stringProvider.getString(R.string.monday)
        WeekDay.TUESDAY -> stringProvider.getString(R.string.tuesday)
        WeekDay.WEDNESDAY -> stringProvider.getString(R.string.wednesday)
        WeekDay.THURSDAY -> stringProvider.getString(R.string.thursday)
        WeekDay.FRIDAY -> stringProvider.getString(R.string.friday)
        WeekDay.SATURDAY -> stringProvider.getString(R.string.saturday)
    }
}

fun abbreviateDays(toAbbreviate: String, stringProvider: StringProvider): String {
    return toAbbreviate
        .replace(
            stringProvider.getString(R.string.sunday),
            stringProvider.getString(R.string.sunday_initial)
        )
        .replace(
            stringProvider.getString(R.string.monday),
            stringProvider.getString(R.string.monday_initial)
        )
        .replace(
            stringProvider.getString(R.string.tuesday),
            stringProvider.getString(R.string.tuesday_initial)
        )
        .replace(
            stringProvider.getString(R.string.wednesday),
            stringProvider.getString(R.string.wednesday_initial)
        )
        .replace(
            stringProvider.getString(R.string.thursday),
            stringProvider.getString(R.string.thursday_initial)
        )
        .replace(
            stringProvider.getString(R.string.friday),
            stringProvider.getString(R.string.friday_initial)
        )
        .replace(
            stringProvider.getString(R.string.saturday),
            stringProvider.getString(R.string.saturday_initial)
        )

}

/**
 * Returns a pair containing the first set of consecutive days followed by the rest of the days in @param days
 */
@Suppress("kotlin:S3776")
fun separateFirstSetOfConsecutiveDays(days: List<WeekDay>): Pair<List<WeekDay>, List<WeekDay>> {

    val properOrderedDays = listOf(
        WeekDay.SUNDAY,
        WeekDay.MONDAY,
        WeekDay.TUESDAY,
        WeekDay.WEDNESDAY,
        WeekDay.THURSDAY,
        WeekDay.FRIDAY,
        WeekDay.SATURDAY
    )
    val consecutives = mutableListOf<WeekDay>()
    val nonConsecutives = mutableListOf<WeekDay>()

    val addConsecutiveNeighborsIfExist = { forDay: WeekDay ->

        val previousDay: WeekDay
        val nextDay: WeekDay

        if (forDay == WeekDay.SUNDAY) {
            nextDay = WeekDay.MONDAY
            if (days.contains(nextDay) && days.contains(WeekDay.TUESDAY)) {
                if (!consecutives.contains(forDay)) {
                    consecutives.add(forDay)
                }
                if (!consecutives.contains(nextDay)) {
                    consecutives.add(nextDay)
                }
                if (!consecutives.contains(WeekDay.TUESDAY)) {
                    consecutives.add(WeekDay.TUESDAY)
                }
            }
        } else if (forDay == WeekDay.SATURDAY) {
            previousDay = WeekDay.FRIDAY
            if (days.contains(previousDay) && days.contains(WeekDay.THURSDAY)) {
                if (!consecutives.contains(WeekDay.THURSDAY)) {
                    consecutives.add(WeekDay.THURSDAY)
                }
                if (!consecutives.contains(previousDay)) {
                    consecutives.add(previousDay)
                }
                if (!consecutives.contains(forDay)) {
                    consecutives.add(forDay)
                }
            }
        } else {
            val dayIndex = properOrderedDays.indexOf(forDay)

            if (dayIndex > 0 && dayIndex < properOrderedDays.size - 1) {
                previousDay = properOrderedDays[dayIndex - 1]
                nextDay = properOrderedDays[dayIndex + 1]
                if (days.contains(previousDay) && days.contains(nextDay)) {
                    if (!consecutives.contains(previousDay)) {
                        consecutives.add(previousDay)
                    }
                    if (!consecutives.contains(forDay)) {
                        consecutives.add(forDay)
                    }
                    if (!consecutives.contains(nextDay)) {
                        consecutives.add(nextDay)
                    }
                }
            }
        }
    }

    days.forEach { addConsecutiveNeighborsIfExist(it) }

    days.forEach {
        if (!consecutives.contains(it) && !nonConsecutives.contains(it)) {
            nonConsecutives.add(it)
        }
    }

    return Pair(consecutives, nonConsecutives)
}