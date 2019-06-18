package com.nosetrap.utillib


import android.content.Context
import android.content.Intent
import android.net.Uri


class IntentUtil(private val context: Context)  {



        /**
         * sendEmail method is used to send email from available email clients installed in the app
         * @param chooserTitle title of the client. Can not be null
         * @param subject email subject. Can not be null.
         * @param body body of the email. Can not be null
         * @param recipients list of recipients. For example, "abc@xyz.com, xyz@dfc.com". this parameter can not be null
         */
        fun sendEmail(chooserTitle: String, subject: String, body: String, vararg recipients: String) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, recipients)
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, body)
            try {
                val chooserIntent = Intent.createChooser(intent, chooserTitle)
                chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(chooserIntent)
            } catch (ex: android.content.ActivityNotFoundException) {
            }

        }

        /**
         *
         */
        fun shareImage(context: Context, imageUri: Uri, title: String) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
            shareIntent.type = "image/jpeg"
            context.startActivity(Intent.createChooser(shareIntent, title))
        }

        /**
         *
         */
        fun shareText(context: Context, text: String) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Text")
            intent.putExtra(Intent.EXTRA_TEXT, text)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(
                Intent.createChooser(intent, "Choose"))
        }

}