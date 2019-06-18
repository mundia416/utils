package com.nosetrap.utillib

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


class KeyboardUtil() {

    companion object {
        /**
         * shows the soft keyboard
         */
        fun showKeyboard(context: Context) {
            toggle(true,context)
        }

        /**
         * hides the soft keyboard
         */
        fun hideKeyboard(context: Context) {
            toggle(false,context)
        }

        /**
         *
         */
        private fun toggle(show: Boolean,context: Context) {
            val imm = getInputMethodManager(context)

            if (show)
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
            else
                imm.hideSoftInputFromWindow(View(context).windowToken, 0)
        }

        /**
         * check if the soft keyboard is currently open
         */
        fun isKeyboardActive(context: Context): Boolean {
            return getInputMethodManager(context).isActive
        }
        /**
         *
         */
        private fun getInputMethodManager(context: Context): InputMethodManager{
            return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }
    }
}