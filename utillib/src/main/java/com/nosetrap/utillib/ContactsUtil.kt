package com.nosetrap.utillib

import android.content.Context
import android.provider.ContactsContract

class ContactsUtil {
    /**
     * load contact data from device
     *
     * @return arrayList of ContactItem
     */
    fun getContactsList(context: Context) : List<ContactItem> {
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
        val sortOrder = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} COLLATE LOCALIZED ASC"
        val numberColumn = ContactsContract.CommonDataKinds.Phone.NUMBER
        val displayNameColumn = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME

        val cursor = context.contentResolver.query(uri, projection, "", emptyArray<String>(), sortOrder)
        val list = generateSequence { if (cursor.moveToNext()) cursor else null }
                .map { ContactItem(it.getString(it.getColumnIndex(displayNameColumn)), it.getString(cursor.getColumnIndex(numberColumn))) }
                .toList()

        cursor.close()

        return list
    }


    /**
     *
     */
    data class ContactItem(var name: String, var phoneNumber: String) {
        override fun toString(): String {
            return "ContactItem name -> $name phoneNumber -> $phoneNumber"
        }
    }
}