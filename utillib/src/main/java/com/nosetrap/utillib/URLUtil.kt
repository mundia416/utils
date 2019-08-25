package com.nosetrap.utillib

class URLUtil {
    companion object {
        fun isUrl(path: String?): Boolean {
            return if (URLUtil.isHttpUrl(path) || URLUtil.isHttpsUrl(path)) {
                true
            } else {
                false
            }
        }
    }
}