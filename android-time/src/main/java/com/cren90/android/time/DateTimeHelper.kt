/**
 * Created by Chris Renfrow on 4/21/20.
 */

package com.cren90.android.time

import com.cren90.android.common.providers.resources.StringProvider
import com.cren90.android.common.providers.system.DateFormatProvider
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate", "unused", "PropertyName")
class DateTimeHelper @Inject constructor(
    val stringProvider: StringProvider,
    val displayTimeZone: TimeZone
) {

    private val utcTimeZone = TimeZone.getTimeZone("UTC")

    //----------------------------------------------------------------------------------------------
    // region Patterns
    //----------------------------------------------------------------------------------------------

    val yyyyMMddTHHmmssSZPattern = "yyyy-MM-dd'T'HH:mm:ss.S'Z'"
    val yyyyMMddTHHmmssZPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val yyyyMMddTHHmmZPattern = "yyyy-MM-dd'T'HH:mm'Z'"
    val yyyyMMddTHHmmssSPattern = "yyyy-MM-dd'T'HH:mm:ss.S"
    val yyyyMMddTHHmmssPattern = "yyyy-MM-dd'T'HH:mm:ss"
    val yyyyMMddTHHmmPattern = "yyyy-MM-dd'T'HH:mm"
    val yyyyMMddHHmmssSZPattern = "yyyy-MM-dd' 'HH:mm:ss.S'Z'"
    val yyyyMMddHHmmssZPattern = "yyyy-MM-dd' 'HH:mm:ss'Z'"
    val yyyyMMddHHmmZPattern = "yyyy-MM-dd' 'HH:mm'Z'"
    val yyyyMMddHHmmssSPattern = "yyyy-MM-dd HH:mm:ss.S"
    val yyyyMMddHHmmssPattern = "yyyy-MM-dd HH:mm:ss"
    val EEEMMMdyyyyHmmaPattern = "EEE MMM d, yyyy h:mm a"
    val MMMddyyyyPattern = "MMM dd, yyyy"
    val MMMdyyyy_at_HmmaPattern = "MMM d, yyyy 'at' h:mm a"
    val MMMdyyyyHmmaPattern = "MMM d, yyyy, h:mm a"
    val MdyyPattern = "M/d/yy"
    val MdyyyyPattern = "M/d/yyyy"
    val MdyyyyHmmaPattern = "M/d/yyyy h:mm a"
    val MMddyyHmmaPattern = "MM/dd/yy h:mm a"
    val HmmaPattern = "h:mm a"
    val HHmmssZPattern = "HH:mm:ss'Z'"
    val HHmmssPattern = "HH:mm:ss"
    val MMMMdyyyyOrdinalPattern = "MMMM d'%s', yyyy"
    val MMMdhmmaOrdinalPattern = "MMMM d'%s' 'at' h:mm a"
    val MMMdyyyyhmmaOrdinalPattern = "MMM d'%s', yyyy 'at' h:mm a"

    //----------------------------------------------------------------------------------------------
    // endregion Patterns
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // region Parsers
    //----------------------------------------------------------------------------------------------

    fun parseDateToCalendar(toParse: String): Calendar {
        val timezone = if (toParse.contains('Z')) utcTimeZone else displayTimeZone

        val calendar = GregorianCalendar(timezone)

        calendar.time = parseDate(toParse) ?: calendar.time

        return calendar
    }

    fun parseDate(toParse: String): Date? {
        val timezone = if (toParse.contains('Z')) utcTimeZone else displayTimeZone
        val serverPatterns = listOf(
            SimpleDateFormat(yyyyMMddTHHmmssSZPattern, Locale.getDefault()).apply {
                timeZone = timezone
            },
            SimpleDateFormat(yyyyMMddTHHmmssZPattern, Locale.getDefault()).apply {
                timeZone = timezone
            },
            SimpleDateFormat(yyyyMMddTHHmmZPattern, Locale.getDefault()).apply {
                timeZone = timezone
            },
            SimpleDateFormat(yyyyMMddTHHmmssSPattern, Locale.getDefault()).apply {
                timeZone = timezone
            },
            SimpleDateFormat(yyyyMMddTHHmmssPattern, Locale.getDefault()).apply {
                timeZone = timezone
            },
            SimpleDateFormat(yyyyMMddTHHmmPattern, Locale.getDefault()).apply {
                timeZone = timezone
            },
            SimpleDateFormat(yyyyMMddHHmmssSZPattern, Locale.getDefault()).apply {
                timeZone = timezone
            },
            SimpleDateFormat(yyyyMMddHHmmssZPattern, Locale.getDefault()).apply {
                timeZone = timezone
            },
            SimpleDateFormat(yyyyMMddHHmmZPattern, Locale.getDefault()).apply {
                timeZone = timezone
            },
            SimpleDateFormat(yyyyMMddHHmmssSPattern, Locale.getDefault()).apply {
                timeZone = timezone
            },
            SimpleDateFormat(yyyyMMddHHmmssPattern, Locale.getDefault()).apply {
                timeZone = timezone
            },
            SimpleDateFormat(MdyyPattern, Locale.getDefault()).apply { timeZone = timezone },
            SimpleDateFormat(MdyyyyPattern, Locale.getDefault()).apply { timeZone = timezone }
        )

        serverPatterns.forEach {
            try {
                return it.parse(toParse) ?: throw ParseException("Parsed but result was null", 0)
            } catch (e: ParseException) {
                // Continue
            }
        }

        return null
    }

    //----------------------------------------------------------------------------------------------
    // endregion Parsers
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // region Formats
    //----------------------------------------------------------------------------------------------

    val yyyyMMddTHHmmssSZ: SimpleDateFormat
        get() = SimpleDateFormat(
            yyyyMMddTHHmmssSZPattern,
            Locale.getDefault()
        ).apply {
            timeZone = utcTimeZone
        }

    val yyyyMMddTHHmmssZ: SimpleDateFormat
        get() = SimpleDateFormat(
            yyyyMMddTHHmmssZPattern,
            Locale.getDefault()
        ).apply {
            timeZone = utcTimeZone
        }

    val yyyyMMddHHmmss: SimpleDateFormat
        get() = SimpleDateFormat(
            yyyyMMddHHmmssPattern,
            Locale.getDefault()
        ).apply {
            timeZone = displayTimeZone
        }

    val MMMddyyyy: SimpleDateFormat
        get() = SimpleDateFormat(MMMddyyyyPattern, Locale.getDefault()).apply {
            timeZone = displayTimeZone
        }

    val MMMdyyyy_at_Hmma: SimpleDateFormat
        get() = SimpleDateFormat(
            MMMdyyyy_at_HmmaPattern,
            Locale.getDefault()
        ).apply {
            timeZone = displayTimeZone
        }

    val MMMdyyyyHmma: SimpleDateFormat
        get() = SimpleDateFormat(
            MMMdyyyyHmmaPattern,
            Locale.getDefault()
        ).apply {
            timeZone = displayTimeZone
        }

    val MMddyyHmma: SimpleDateFormat
        get() = SimpleDateFormat(
            MMddyyHmmaPattern,
            Locale.getDefault()
        ).apply {
            timeZone = displayTimeZone
        }

    val Mdyy: SimpleDateFormat
        get() = SimpleDateFormat(MdyyPattern, Locale.getDefault()).apply {
            timeZone = displayTimeZone
        }

    val Mdyyyy: SimpleDateFormat
        get() = SimpleDateFormat(MdyyyyPattern, Locale.getDefault()).apply {
            timeZone = displayTimeZone
        }
    val MdyyyyHmma: SimpleDateFormat
        get() = SimpleDateFormat(MdyyyyHmmaPattern, Locale.getDefault()).apply {
            timeZone = displayTimeZone
        }
    val EEEMMMdyyyyHmma: SimpleDateFormat
        get() = SimpleDateFormat(EEEMMMdyyyyHmmaPattern, Locale.getDefault()).apply {
            timeZone = displayTimeZone
        }
    val Hmma: SimpleDateFormat
        get() = SimpleDateFormat(HmmaPattern, Locale.getDefault()).apply {
            timeZone = displayTimeZone
        }
    val HHmmssZ: SimpleDateFormat
        get() = SimpleDateFormat(HHmmssZPattern, Locale.getDefault()).apply {
            timeZone = utcTimeZone
        }
    val HHmmss: SimpleDateFormat
        get() = SimpleDateFormat(HHmmssPattern, Locale.getDefault()).apply {
            timeZone = displayTimeZone
        }
    val HHmmssUtc: SimpleDateFormat
        get() = SimpleDateFormat(HHmmssPattern, Locale.getDefault()).apply {
            timeZone = utcTimeZone
        }
    val MMMMdyyyyOrdinal: SimpleDateFormat
        get() = SimpleDateFormat(MMMMdyyyyOrdinalPattern, Locale.getDefault()).apply {
            timeZone = displayTimeZone
        }
    val MMMdhmmaOrdinal: SimpleDateFormat
        get() = SimpleDateFormat(MMMdhmmaOrdinalPattern, Locale.getDefault()).apply {
            timeZone = displayTimeZone
        }
    val MMMdyyyyhmmaOrdinal: SimpleDateFormat
        get() = SimpleDateFormat(MMMdyyyyhmmaOrdinalPattern, Locale.getDefault()).apply {
            timeZone = displayTimeZone
        }

    //----------------------------------------------------------------------------------------------
    // endregion Formats
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // region User readable formats
    //----------------------------------------------------------------------------------------------

    fun formatDateMMMddyyyy(input: Date): String {
        return MMMddyyyy.format(input).replace("May.", "May")
    }

    fun formatDateMMMdhmmaOrdinal(input: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = input

        return String.format(
            MMMdhmmaOrdinal.format(input).replace("May.", "May"),
            getDayOfMonthSuffix(calendar.get(Calendar.DATE))
        )
    }

    fun formatDateMMMMddyyyyOrdinal(input: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = input

        return String.format(
            MMMMdyyyyOrdinal.format(input),
            getDayOfMonthSuffix(calendar.get(Calendar.DATE))
        )
    }

    fun formatDateMMMdyyyyhmmaOrdinal(input: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = input

        return String.format(
            MMMdyyyyhmmaOrdinal.format(input),
            getDayOfMonthSuffix(calendar.get(Calendar.DATE))
        )
    }

    fun formatDateMMddyyHmma(input: Date): String {
        return MMddyyHmma.format(input)
    }

    /**
     * Helper method which will return a shorthand formatted version of the passed in Calendar.
     * The format will be "Today" if the Calendar's date matches today's date, "Yesterday" if
     * the Calendar's date matches yesterday's date, and "M/d/yy" for all dates before that
     *
     * @param then The calendar which should be formatted to short hand
     * @return The formatted date
     */
    fun getShortHandHumanReadableDate(
        now: Calendar,
        then: Calendar
    ): String {

        val nowDayOfYear = now.get(Calendar.DAY_OF_YEAR)
        val thenDayOfYear = then.get(Calendar.DAY_OF_YEAR)

        return if (nowDayOfYear == thenDayOfYear) {
            stringProvider.getString(R.string.today)

        } else if (nowDayOfYear - 1 == thenDayOfYear || nowDayOfYear == 1 && thenDayOfYear >= 365) {
            stringProvider.getString(R.string.yesterday)

        } else {
            Mdyy.format(then.time)
        }
    }

    fun getShortHandHumanReadableDate(thenString: String): String {
        val now = Calendar.getInstance()
        val thenDate = MMMddyyyy.parse(thenString)
        val then = Calendar.getInstance()

        thenDate?.let {
            then.time = it
        }

        val nowDayOfYear = now.get(Calendar.DAY_OF_YEAR)
        val thenDayOfYear = then.get(Calendar.DAY_OF_YEAR)

        return if (nowDayOfYear == thenDayOfYear) {
            stringProvider.getString(R.string.today)

        } else if (nowDayOfYear - 1 == thenDayOfYear || nowDayOfYear == 1 && thenDayOfYear >= 365) {
            stringProvider.getString(R.string.yesterday)

        } else {
            Mdyy.format(then.time)
        }
    }

    private fun getDayOfMonthSuffix(n: Int): String? {
        if (n < 1 || n > 31) {
            return null
        }

        if (n in 11..13) {
            return "th"
        }
        return when (n % 10) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }

    //----------------------------------------------------------------------------------------------
    // endregion User readable formats
    //----------------------------------------------------------------------------------------------

    companion object {

        @JvmStatic
        fun setTimeZoneLeaveTime(
            input: Calendar,
            timeZone: TimeZone
        ): Calendar {
            val outputTime = Calendar.getInstance(timeZone)
            for (i in 0 until Calendar.FIELD_COUNT) {
                if (i != Calendar.ZONE_OFFSET && i != Calendar.DST_OFFSET) {
                    outputTime.set(i, input.get(i))
                }
            }

            return outputTime.clone() as Calendar
        }

        @JvmStatic
        fun getTimeDifferenceString(
            earlyCalendar: Calendar,
            lateCalendar: Calendar,
            precision: TimeUnit,
            stringProvider: StringProvider
        ): String {

            var millis = lateCalendar.timeInMillis - earlyCalendar.timeInMillis

            val days = millis / MILLIS_PER_DAY
            millis -= (MILLIS_PER_DAY * days)

            val hours = millis / MILLIS_PER_HOUR
            millis -= (MILLIS_PER_HOUR * hours)

            val minutes = millis / MILLIS_PER_MINUTE
            millis -= (MILLIS_PER_MINUTE * minutes)

            val seconds = millis / MILLIS_PER_SECOND
            millis -= (MILLIS_PER_SECOND * seconds)

            val showDays = when (precision) {
                TimeUnit.DAYS,
                TimeUnit.HOURS,
                TimeUnit.MINUTES,
                TimeUnit.SECONDS,
                TimeUnit.MILLISECONDS,
                TimeUnit.MICROSECONDS,
                TimeUnit.NANOSECONDS -> days > 0
                else                 -> false
            }

            val showHours = when (precision) {
                TimeUnit.HOURS,
                TimeUnit.MINUTES,
                TimeUnit.SECONDS,
                TimeUnit.MILLISECONDS,
                TimeUnit.MICROSECONDS,
                TimeUnit.NANOSECONDS -> hours > 0
                else                 -> false
            }

            val showMinutes = when (precision) {
                TimeUnit.MINUTES,
                TimeUnit.SECONDS,
                TimeUnit.MILLISECONDS,
                TimeUnit.MICROSECONDS,
                TimeUnit.NANOSECONDS -> minutes > 0
                else                 -> false
            }

            val showSeconds = when (precision) {
                TimeUnit.SECONDS,
                TimeUnit.MILLISECONDS,
                TimeUnit.MICROSECONDS,
                TimeUnit.NANOSECONDS -> seconds > 0
                else                 -> false
            }

            val showMillis = when (precision) {
                TimeUnit.MILLISECONDS,
                TimeUnit.MICROSECONDS,
                TimeUnit.NANOSECONDS -> millis > 0
                else                 -> false
            }


            var string = ""

            string += if (showDays) days.toString() + " " + stringProvider.getString(if (days > 1) R.string.days else R.string.day) + " " else ""
            string += if (showHours) hours.toString() + " " + stringProvider.getString(if (hours > 1) R.string.hours else R.string.hour) + " " else ""
            string += if (showMinutes) minutes.toString() + " " + stringProvider.getString(if (minutes > 1) R.string.minutes else R.string.minute) + " " else ""
            string += if (showSeconds) seconds.toString() + " " + stringProvider.getString(if (seconds > 1) R.string.seconds else R.string.second) + " " else ""
            string += if (showMillis) millis.toString() + " " + stringProvider.getString(if (millis > 1) R.string.milliseconds else R.string.millisecond) + " " else ""

            return string.trim()
        }

        /**
         * Converts hour of day and minute to the appropriately formatted string
         * @param hour 24 hour based hour of the day
         * @param minute minutes of hour (0 - 59)
         * @param dateFormatProvider to retrieve date format info from
         * @param stringProvider to retrieve strings from
         * @return string of the format 01:00 or 1:00 AM, 13:00 or 1:00 PM based on system setting
         */
        @JvmStatic
        fun timeToString(
            hour: Int,
            minute: Int,
            dateFormatProvider: DateFormatProvider,
            stringProvider: StringProvider
        ): String {

            val is24 = dateFormatProvider.is24Hour()

            var formatString = "%02d:%02d %s"

            var internalHour = hour

            if (!is24) {
                formatString = "%d:%02d %s"

                internalHour = hour.rem(12)

                if (internalHour == 0) {
                    internalHour = 12
                }
            }

            val amPmString = when {
                is24       -> ""
                hour >= 12 -> stringProvider.getString(R.string.pm)
                else       -> stringProvider.getString(R.string.am)
            }

            return String.format(formatString, internalHour, minute, amPmString)
        }

        @JvmStatic
        fun hhmmssToMillisAfterMidnight(hhmmss24Hour: String): Int {
            val timeSplit = hhmmss24Hour.split(":")

            var hour = 0
            var minute = 0
            var second = 0

            if (timeSplit.isNotEmpty()) {
                hour = timeSplit[0].toIntOrNull() ?: 0
            }

            if (timeSplit.size >= 2) {
                minute = timeSplit[1].toIntOrNull() ?: 0
            }

            if (timeSplit.size >= 3) {
                second = timeSplit[2].toIntOrNull() ?: 0
            }
            return MILLIS_PER_HOUR * hour + MILLIS_PER_MINUTE * minute + MILLIS_PER_SECOND * second
        }
    }
}