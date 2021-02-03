package com.mtek.goarenopoc.utils

import android.annotation.SuppressLint
import android.text.TextUtils
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object  DateManager {
    @SuppressLint("SimpleDateFormat")
    fun getFromattedTime(time: String?): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
        val dateFormatted: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        return try {
            val dateAt = dateFormat.parse(time)
            dateFormatted.format(dateAt)
        } catch (e: Exception) {
            // e.printStackTrace();
            ""
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getFromattedTime2(time: String?): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
        val dateFormatted: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        return try {
            val dateAt = dateFormat.parse(time)
            dateFormatted.format(dateAt)
        } catch (e: Exception) {
            // e.printStackTrace();
            ""
        }
    }

    fun getFromattedOrderDate(time: String?): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
        val dateFormatted: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
        return try {
            val dateAt = dateFormat.parse(time)
            dateFormatted.format(dateAt)
        } catch (e: Exception) {
            // e.printStackTrace();
            ""
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getFormattedRenderDate(time: String?): String {
        var originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
        var dateFormatted: DateFormat = SimpleDateFormat("dd.MM.yyyy")
        return try {
            var dateAt = originalFormat.parse(time)
            dateFormatted.format(dateAt)
        } catch (e: Exception) {
            // e.printStackTrace();
            ""
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun getFormattedRenderDateWithoutGMT(time: String?): String {
        val originalFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US)
        val dateFormatted: DateFormat = SimpleDateFormat("dd.MM.yyyy")
        return try {
            val dateAt = originalFormat.parse(time)
            dateFormatted.format(dateAt)
        } catch (e: Exception) {
            // e.printStackTrace();
            ""
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun getFormattedDescriptionDate(time: String?): String {
        val originalFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US)
        val dateFormatted: DateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return try {
            val dateAt = originalFormat.parse(time)
            dateFormatted.format(dateAt)
        } catch (e: Exception) {
            // e.printStackTrace();
            ""
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun getFormattedRenderDateWithSecondReturn(time: String?): String {
        val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ", Locale.US)
        val dateFormatted: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm")
        return try {
            val dateAt = originalFormat.parse(time)
            dateFormatted.format(dateAt)
            //            if (time != null) {
            //                Date dateAt = originalFormat.parse(time);
            //                String returnDate = dateFormatted.format(dateAt);
            //                return returnDate;
            //            } else {
            //                return "";
            //            }
        } catch (e: Exception) {
            // e.printStackTrace();
            ""
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun getFormattedDateTime(time: String?, format: String?): String {
        val originalFormat: DateFormat = SimpleDateFormat(format, Locale.US)
        val dateFormatted: DateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return try {
            val dateAt = originalFormat.parse(time)
            dateFormatted.format(dateAt)
        } catch (e: Exception) {
            // e.printStackTrace();
            ""
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun getFormattedRenderDateWithSplitSeconds(time: String?): String {
        val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ", Locale.US)
        val dateFormatted: DateFormat = SimpleDateFormat("dd.MM.yyyy")
        return try {
            val dateAt = originalFormat.parse(time)
            dateFormatted.format(dateAt)
        } catch (e: ParseException) {
            // e.printStackTrace();
            ""
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun getFormattedRenderTime(time: String?): String {
        val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US)
        val timeFormatted: DateFormat = SimpleDateFormat("HH:mm")
        return try {
            val dateAt = originalFormat.parse(time)
            timeFormatted.format(dateAt)
        } catch (e: Exception) {
            // e.printStackTrace();
            ""
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun getBirthdateFromattedTime(time: String?): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val dateFormatted: DateFormat = SimpleDateFormat("dd.MM.yyyy")
        return try {
            val dateAt = dateFormat.parse(time)
            dateFormatted.format(dateAt)
        } catch (e: Exception) {
            // e.printStackTrace();
            ""
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun getDateFormattedReverse(time: String?): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        val dateFormatted: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        return try {
            val dateAt = dateFormat.parse(time)
            dateFormatted.format(dateAt)
        } catch (e: Exception) {
            // e.printStackTrace();
            ""
        }
    }

    //1992-05-24T00:00:00+03:00
    //TODO All task description time check
    val orderCompleteFormatTime: String
        get() {
            val dateFormat = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
            )
            dateFormat.timeZone = TimeZone.getTimeZone("GMT+3")
            return dateFormat.format(Date())
        }

    val orderCompleteFormatTimeDescription: String
        get() {
            val dateFormat = SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss", Locale.US
            )
            dateFormat.timeZone = TimeZone.getTimeZone("GMT+3")
            return dateFormat.format(Date())
        }
    val orderCompleteFormatTimeDescriptions: String
        get() {
            val dateFormat = SimpleDateFormat(
                "yyyy.MM.dd HH:mm:ss", Locale.US
            )
            dateFormat.timeZone = TimeZone.getTimeZone("GMT+3")
            return dateFormat.format(Date())
        }

    fun formatTheBirthdate(time: String?): String {
        val dateFormatAt: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
        )
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return try {
            val date = dateFormatAt.parse(time)
            dateFormat.format(date)
        } catch (e: Exception) {
            // e.printStackTrace();
            ""
        }
    }

    val currentDate: String
        get() {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            val now = Date()
            return dateFormat.format(now)
        }
    @SuppressLint("SimpleDateFormat")
    fun getTimeYearDifference(time: String?): Int {
        return try {
            val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy")
            val dateNow = Date()
            val dateAt = dateFormat.parse(time)
            val diff_in_second = ((dateNow.time - dateAt.time) / 1000).toInt()
            var dayDifferent = 0
            dayDifferent = diff_in_second / (60 * 60 * 24)
            dayDifferent / 365
        } catch (e: Exception) {
            // System.out.println("error");
            0
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun getTimeYearDifferenceFromId(time: String?): Int {
        return try {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dateNow = Date()
            val dateAt = dateFormat.parse(time)
            val diff_in_second = ((dateNow.time - dateAt.time) / 1000).toInt()
            var dayDifferent = 0
            dayDifferent = diff_in_second / (60 * 60 * 24)
            dayDifferent / 365
        } catch (e: Exception) {
            // System.out.println("error");
            0
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun getTimeYearDifferenceFromMernisId(time: String?): Int {
        return try {
            val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy")
            val dateNow = Date()
            val dateAt = dateFormat.parse(time)
            //int diff_in_second = (int) ((dateAt.getTime()- dateNow.getTime()) / 1000);
            //int dayDifferent = 0;

            //dayDifferent =  (diff_in_second / (60 * 60 * 24));
            //int yearDifferent = dayDifferent / 365;
            //return yearDifferent;
            val now = Calendar.getInstance()
            val before = Calendar.getInstance()
            before.time = dateAt
            var age = now[Calendar.YEAR] - before[Calendar.YEAR]
            if (now[Calendar.DAY_OF_YEAR] < before[Calendar.DAY_OF_YEAR]) {
                age--
            }
            age
        } catch (e: Exception) {
            // System.out.println("error");
            0
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun dateToString(date: Date?): String {
        return try {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
            dateFormat.format(date)
        } catch (e: Exception) {
            // System.out.println("error");
            ""
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun formatDate(currentDate: String?, currentDateFormat: String?, targetDateFormat: String?): String? {
        var result: String?
        var sdf: SimpleDateFormat
        if (TextUtils.isEmpty(currentDate)) {
            return ""
        }
        try {
            sdf = SimpleDateFormat(currentDateFormat)
            val date = sdf.parse(currentDate)
            sdf = SimpleDateFormat(targetDateFormat)
            result = sdf.format(date)
        } catch (e: Exception) {
            // e.printStackTrace();
            result = currentDate
        }
        return result
    }

    var dateFormats = arrayOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ssZ",
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd HH:mm:ss"
    )
    @SuppressLint("SimpleDateFormat")
    fun formatDate(currentDate: String?, targetDateFormat: String?): String? {
        var result = currentDate
        var sdf: SimpleDateFormat
        if (TextUtils.isEmpty(currentDate)) {
            return ""
        }
        for (format in dateFormats) {
            try {
                sdf = SimpleDateFormat(format)
                val date = sdf.parse(currentDate)
                sdf = SimpleDateFormat(targetDateFormat)
                result = sdf.format(date)
                break
            } catch (e: Exception) {
            }
        }
        return result
    }
}