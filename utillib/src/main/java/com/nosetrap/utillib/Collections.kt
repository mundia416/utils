package com.nosetrap.utillib

import android.R.attr.keySet



class Collections {
    companion object {
        /**
         *
         */
        fun getKeyFromValue(map: Map<*, *>, value: Any): Any? {
            for (key in map.keys) {
                if (map[key] == value) {
                    return key
                }
            }
            return null
        }
    }
}