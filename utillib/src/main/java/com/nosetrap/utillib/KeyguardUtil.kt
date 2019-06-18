package com.nosetrap.utillib

import android.content.Context.KEYGUARD_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.KeyguardManager
import android.content.Context


class KeyguardUtil{
    companion object {
        fun isScreenLocked(context: Context): Boolean {
            val kgm = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager?
            return kgm != null && kgm.isKeyguardLocked
        }
    }

}