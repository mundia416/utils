package com.nosetrap.utillib

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class ClipboardUtil(private val context: Context) {
    private val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    /**
     * copy text to clipboard
     *
     * @param[text] to copy
     */
    fun copyText(text: CharSequence) {
        clipboardManager.primaryClip = ClipData.newPlainText("text", text)
    }

    /**
     * get text of clipboard list
     *
     * @return first object of primaryClip list
     */
    fun getTextFromClipboard(): String {
        val clip = clipboardManager.primaryClip

        return if (clip != null && clip.itemCount > 0) clip.getItemAt(0).coerceToText(context).toString() else ""
    }
}