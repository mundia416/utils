package com.nosetrap.utillib

import android.content.Context
import java.util.concurrent.TimeUnit
import java.text.SimpleDateFormat
import java.util.*
import java.text.ParseException


class DateUtil(private val context: Context) {
    companion object {
        /**
         * Typical MySqL/SQL dateTime format with dash as separator
         */
        const val DATE_TIME_PATTERN_1 = "yyyy-MM-dd HH:mm:ss"
        /**
         * Typical MySqL/SQL dateTime format with slash as seperator
         */
        const val DATE_TIME_PATTERN_2 = "dd/MM/yyyy HH:mm:ss"
        /**
         * Typical MySqL/SQL date format with dash as separator
         */
        const val DATE_PATTERN_1 = "yyyy-MM-dd"
        /**
         * Typical MySqL/SQL date format with slash as seperator
         */
        const val DATE_PATTERN_2 = "dd/MM/yyyy"
        /**
         * Time format full
         */
        const val TIME_PATTERN_1 = "HH:mm:ss"

        /**
         * 24 hour time
         */
        const val TIME_PATTERN_2 = "HH:mm"

        /**
         * 12 hour time
         */
        const val TIME_PATTERN_3 = "hh:mm"

        /**
         * get the current time
         * @param format is the format used on simpleDateFormat
         */
        fun getTime(format: String = TIME_PATTERN_2): String {
            val c = Calendar.getInstance()
            return formatDate(c.time)
        }
        /**
         * Convert a Java Date object to String
         *
         * @param date Date Object
         * @return Date Object string representation
         */
        fun formatDate(date: Date, format: String = DATE_TIME_PATTERN_1): String {
            val sdf = SimpleDateFormat(format)
            return sdf.format(date)
        }

        /**
         * Convert a date string to Java Date Object
         *
         * @param dateString Date String
         * @return Java Date Object
         */
        fun formatDate(dateString: String): Date? {
            val sdf = SimpleDateFormat(getDatePattern(dateString))
            var date: Date? = null
            try {
                date = sdf.parse(dateString.trim { it <= ' ' })
            } catch (e: ParseException) {
            }
            return date
        }

        /**
         * Get Date or DateTime formatting pattern
         *
         * @param dateString Date String
         * @return Format Pattern
         */
        private fun getDatePattern(dateString: String): String {
            return if (isDateTime(dateString)) {
                if (dateString.contains("/")) DATE_TIME_PATTERN_2 else DATE_TIME_PATTERN_1
            } else {
                if (dateString.contains("/")) DATE_PATTERN_2 else DATE_PATTERN_1
            }
        }

        /**
         * Tell whether or not a given string represent a date time string or a simple date
         *
         * @param dateString Date String
         * @return True if given string is a date time False otherwise
         */
        fun isDateTime(dateString: String?): Boolean {
            return dateString != null && dateString.trim { it <= ' ' }.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size > 1
        }
    }

        /**
         *
         */
        fun getMonthName(month: Int,format: MonthFormat): String {
            //todo localise the strings
            return when(month) {
                Calendar.JANUARY -> if (format == MonthFormat.FORMAT_SHORT) context.getString(R.string.jan) else context.getString(R.string.january)
                Calendar.FEBRUARY -> if (format == MonthFormat.FORMAT_SHORT) context.getString(R.string.feb) else context.getString(R.string.february)
                Calendar.MARCH -> if (format == MonthFormat.FORMAT_SHORT) context.getString(R.string.mar) else context.getString(R.string.march)
                Calendar.APRIL -> if (format == MonthFormat.FORMAT_SHORT) context.getString(R.string.apr) else context.getString(R.string.april)
                Calendar.MAY -> if (format == MonthFormat.FORMAT_SHORT) context.getString(R.string.may) else context.getString(R.string.may)
                Calendar.JUNE -> if (format == MonthFormat.FORMAT_SHORT) context.getString(R.string.jun) else context.getString(R.string.june)
                Calendar.JULY -> if (format == MonthFormat.FORMAT_SHORT) context.getString(R.string.jul) else context.getString(R.string.july)
                Calendar.AUGUST -> if (format == MonthFormat.FORMAT_SHORT) context.getString(R.string.aug) else context.getString(R.string.august)
                Calendar.SEPTEMBER -> if (format == MonthFormat.FORMAT_SHORT) context.getString(R.string.sep) else context.getString(R.string.september)
                Calendar.OCTOBER -> if (format == MonthFormat.FORMAT_SHORT) context.getString(R.string.oct) else context.getString(R.string.october)
                Calendar.NOVEMBER -> if (format == MonthFormat.FORMAT_SHORT) context.getString(R.string.nov) else context.getString(R.string.november)
                Calendar.DECEMBER -> if (format == MonthFormat.FORMAT_SHORT) context.getString(R.string.dec) else context.getString(R.string.december)
                else -> ""
            }
        }

    /**
     * Returns the given duration in a human-friendly format. For example,
     * "4 minutes" or "1 second". Returns only the largest meaningful unit of time,
     * from seconds up to hours.
     */
    fun formatDuration(duration: Long): CharSequence {
        val totalMinutes = TimeUnit.MILLISECONDS.toMinutes(duration)
        val totalHours =  TimeUnit.MILLISECONDS.toHours(duration)

        val hours = totalHours
        val minutes = totalMinutes % 60

        var string = ""
        if(hours == 1L){
            string += String.format(context.getString(R.string.hour),hours)
        }else if(hours > 1L){
            string += String.format(context.getString(R.string.hours),hours)
        }

        if(minutes == 1L){
            string += String.format(context.getString(R.string.minute),hours)
        }else if(minutes > 1L){
            string += String.format(context.getString(R.string.minutes),minutes)
        }

        return  string
    }

    /**
     * get the difference between the startMillis and endMillis in terms of the unit specified
     * in @param[unit]
     * the number returned is always positive
     */
    fun getTimeDiff(startMillis: Long,endMillis: Long,unit: DateUnit): Long{
        val diff = endMillis - startMillis
        val diffDate = when(unit){
            DateUnit.SECOND -> TimeUnit.MILLISECONDS.toSeconds(diff)
            DateUnit.MINUTE -> TimeUnit.MILLISECONDS.toMinutes(diff)
            DateUnit.HOUR -> TimeUnit.MILLISECONDS.toHours(diff)
            DateUnit.DAY -> TimeUnit.MILLISECONDS.toDays(diff)
        }

        if(diffDate < 0){
            diffDate * -1
        }

        return diffDate
    }


    /**
     *             //used with getting the month string
     */
    enum class MonthFormat {
        /**
         * short format of the month, i.e jan,feb
         */
        FORMAT_SHORT,
        /**
         * full format of the month, i.e january,february
         */
        FORMAT_FULL
    }


    /**
     *
     */
    enum class DateUnit{
        DAY,HOUR,MINUTE,SECOND
    }
}