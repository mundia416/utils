package com.nosetrap.utillib

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import java.io.File
import android.os.Build
import java.util.*


class DeviceUtil {
    companion object {

        /**
         *
         */
        fun getCountryCode(): String {
            return Locale.getDefault().country
        }

        /**
         * gets the language currently being used on the device
         */
        fun getCurrentLanguage(): String {
            return Locale.getDefault().language
        }

        /**
         *
         */
        fun getDeviceId(contentResolver: ContentResolver): String{
            val id = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            return id
        }

        /**
         *
         */
        @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
        fun getDeviceIMEI(context: Context): String {
            var imei = ""
            try {
                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

                imei = if (android.os.Build.VERSION.SDK_INT >= 26) {
                    telephonyManager.imei
                } else {
                    telephonyManager.deviceId
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            return imei
        }

        /**
         * check if the device is rooted
         */
        fun isDeviceRooted(): Boolean {
            val su = "su"
            val locations = arrayOf("/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                    "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/")
            for (location in locations) {
                if (File(location + su).exists()) {
                    return true
                }
            }
            return false
        }

        //For get sdk version
        fun getSDKVersion(): Int {
            return android.os.Build.VERSION.SDK_INT
        }

        //For get Android ID
        fun getAndroidID(contentResolver: ContentResolver): String {
            return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        }

        //For get device manufacture
        fun getDeviceManufacturer(): String {
            return Build.MANUFACTURER
        }


        /**
         * Return whether the device is phone.
         *
         * @return `true`: yes<br></br>`false`: no
         */
        fun isPhone(context: Context): Boolean {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            return tm.phoneType != TelephonyManager.PHONE_TYPE_NONE
        }

        //For get device model
        fun getDeviceModel(): String {
            var model: String? = Build.MODEL
            model = if (model != null) {
                model.trim { it <= ' ' }.replace("\\s*".toRegex(), "")
            } else {
                ""
            }
            return model
        }
    }
}