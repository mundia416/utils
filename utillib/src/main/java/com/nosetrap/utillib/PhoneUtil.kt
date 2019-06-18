package com.nosetrap.utillib

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.TelephonyManager
import android.app.PendingIntent
import android.text.TextUtils
import android.Manifest.permission.SEND_SMS
import android.telephony.SmsManager
import androidx.annotation.RequiresPermission






class PhoneUtil(private val  context: Context) {
    companion object {

        /**
         * make a phone call
         * @param phone the phone number to call
         * @return true if successfly dialed, false if something has gone wrong or the device does not
         * support calling
         */
        fun callPhone(phone: String, context: Context): Boolean {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return if (tm.phoneType == TelephonyManager.PHONE_TYPE_NONE) {
                false
            } else {
                try {
                    val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:$phone")
                    context.startActivity(callIntent)
                    true
                }catch (e: Exception){
                    false
                }
            }
        }

        /**
         * send an sms message
         */
        fun sendMessage(context: Context,phone: String,smsContent: String){
            val uri = Uri.parse("smsto:$phone")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", smsContent)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }


    /**
     * Returns the current phone type.
     *
     * @return the current phone type
     *
     *  * [TelephonyManager.PHONE_TYPE_NONE]
     *  * [TelephonyManager.PHONE_TYPE_GSM]
     *  * [TelephonyManager.PHONE_TYPE_CDMA]
     *  * [TelephonyManager.PHONE_TYPE_SIP]
     *
     */
    fun getPhoneType(): Int {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        return tm.phoneType
    }

    /**
     * Return whether sim card state is ready.
     *
     * @return `true`: yes<br></br>`false`: no
     */
    fun isSimCardReady(): Boolean {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        return tm.simState == TelephonyManager.SIM_STATE_READY
    }

    /**
     * Return the sim operator name.
     *
     * @return the sim operator name
     */
    fun getSimOperatorName(): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        return tm.simOperatorName
    }

    /**
     * Send sms silently.
     *
     * Must hold `<uses-permission android:name="android.permission.SEND_SMS" />`
     *
     * @param phoneNumber The phone number.
     * @param content     The content.
     */
    @RequiresPermission(SEND_SMS)
    fun sendSmsSilent(phoneNumber: String, content: String) {
        if (TextUtils.isEmpty(content)) return
        val sentIntent = PendingIntent.getBroadcast(context, 0, Intent("send"), 0)
        val smsManager = SmsManager.getDefault()
        if (content.length >= 70) {
            val ms = smsManager.divideMessage(content)
            for (str in ms) {
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null)
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null)
        }
    }

    /**
     * dial a number
     * @param phone the phone number to call
     * @return true if successfly dialed, false if something has gone wrong or the device does not
     * support calling
     */
    fun dialPhone(phone: String): Boolean {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (tm.phoneType == TelephonyManager.PHONE_TYPE_NONE) {
            false
        } else {
            try {
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:$phone")
                context.startActivity(callIntent)
                true
            }catch (e: Exception){
                false
            }
        }
    }

}