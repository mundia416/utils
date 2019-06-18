package com.nosetrap.utillib

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import android.view.View
import android.webkit.MimeTypeMap


class BitmapUtil(private val context: Context) {

    companion object {

        /**
         * rotate bitmap
         *
         * @param[bitmap] Bitmap which rotate
         * @param[degree] rotate amount
         */
        fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
            val mat = Matrix()
            mat.postRotate(degree.toFloat())
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, mat, true)
        }

        /**
         * create a bitmap from a view
         */
        fun getBitmapFromView(view: View): Bitmap {
            val bitmap = Bitmap.createBitmap(view.width, view.height,
                    Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)

            view.layout(view.left, view.top, view.right,
                    view.bottom)
            view.draw(canvas)

            return bitmap
        }

        /**
         *
         */
        fun getByteFromBitmap(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            return stream.toByteArray()
        }

        /**
         *
         */
        fun getBitmapFromByte(bytes: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        /**
         *
         */
        fun getBitmapFromFile(path: String): Bitmap? {
            val imageFile = File(path)
            var bitmap: Bitmap? = null
            if (imageFile.exists()) {
                bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            }

            return bitmap
        }

        /**
         *add a radius to the corners of a bitmap
         * @return Bitmap object
         */
        fun bitmapCorners(bitmap: Bitmap, radius: Float): Bitmap {
            val width = bitmap.width
            val height = bitmap.height
            val bitmap = Bitmap.createBitmap(width, height, bitmap.config)
            val paint = Paint()
            val canvas = Canvas(bitmap)
            val rect = Rect(0, 0, width, height)

            paint.isAntiAlias = true
            canvas.drawRoundRect(RectF(rect), radius, radius, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)

            bitmap.recycle()
            return bitmap
        }

        /**
         *
         */
        fun getBitmapFromURI(uri: Uri, context: Context): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)

            } catch (e: IOException) {
                e.printStackTrace()
            }

            return bitmap
        }

        /**
         * Convert Drawable to Bitmap in safe way
         *
         * @param[drawable] to convert
         * @return Bitmap object
         */
        fun drawableToBitmap(drawable: Drawable): Bitmap {
            if (drawable is BitmapDrawable) {
                return drawable.bitmap
            }

            var width = drawable.intrinsicWidth
            width = if (width > 0) width else 1
            var height = drawable.intrinsicHeight
            height = if (height > 0) height else 1

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)

            return bitmap
        }

        /**
         * Bitmap to Drawable
         *
         * @param[bitmap] to convert
         * @return Drawable Object
         */
        fun bitmapToDrawable(context: Context, bitmap: Bitmap): Drawable = BitmapDrawable(context.resources, bitmap)


        /**
         * Resizing image
         *
         * @param[width] desire width
         * @param[height] desire height
         * @param[mode] Resizing mode
         * @param[isExcludeAlpha] true - exclude alpha (copy as RGB_565) false - include alpha (copy as ARGB_888)
         */
        // @JvmOverloads
        fun resize(bitmap: Bitmap, width: Int, height: Int, mode: ResizeMode = Companion.ResizeMode.AUTOMATIC,
                   isExcludeAlpha: Boolean = false): Bitmap {
            var mWidth = width
            var mHeight = height
            var mMode = mode
            val sourceWidth = bitmap.width
            val sourceHeight = bitmap.height

            if (mode == Companion.ResizeMode.AUTOMATIC) {
                mMode = calculateResizeMode(sourceWidth, sourceHeight)
            }

            if (mMode == Companion.ResizeMode.FIT_TO_WIDTH) {
                mHeight = calculateHeight(sourceWidth, sourceHeight, width)
            } else if (mMode == Companion.ResizeMode.FIT_TO_HEIGHT) {
                mWidth = calculateWidth(sourceWidth, sourceHeight, height)
            }

            val config = if (isExcludeAlpha) Bitmap.Config.RGB_565 else Bitmap.Config.ARGB_8888
            return Bitmap.createScaledBitmap(bitmap, mWidth, mHeight, true).copy(config, true)
        }

        private fun calculateResizeMode(width: Int, height: Int): ResizeMode =
                if (Companion.ImageOrientation.getOrientation(width, height) === Companion.ImageOrientation.LANDSCAPE) {
                    Companion.ResizeMode.FIT_TO_WIDTH
                } else {
                    Companion.ResizeMode.FIT_TO_HEIGHT
                }

        private fun calculateWidth(originalWidth: Int, originalHeight: Int, height: Int): Int = Math.ceil(originalWidth / (originalHeight.toDouble() / height)).toInt()

        private fun calculateHeight(originalWidth: Int, originalHeight: Int, width: Int): Int = Math.ceil(originalHeight / (originalWidth.toDouble() / width)).toInt()

        /**
         *
         */
        enum class ResizeMode {
            AUTOMATIC, FIT_TO_WIDTH, FIT_TO_HEIGHT, FIT_EXACT
        }


        /**
         *
         */
        private enum class ImageOrientation {
            PORTRAIT, LANDSCAPE;

            companion object {
                fun getOrientation(width: Int, height: Int): ImageOrientation =
                        if (width >= height) LANDSCAPE else PORTRAIT
            }
        }
    }

    /**
     * Attempt to retrieve the thumbnail of given File from the MediaStore. This
     * should not be called on the UI thread.
     */
    fun getThumbnail(file: File): Bitmap? {
        return getThumbnail(getUri(file), getMimeType(file))
    }

    /**
     * Attempt to retrieve the thumbnail of given Uri from the MediaStore. This
     * should not be called on the UI thread.
     */
    fun getThumbnail(uri: Uri): Bitmap? {
        return getThumbnail(uri, getMimeType(File(uri.path)))
    }

    /**
     * Attempt to retrieve the thumbnail of given Uri from the MediaStore. This
     * should not be called on the UI thread.
     */
    fun getThumbnail(uri: Uri?, mimeType: String): Bitmap? {
        if (!isMediaUri(uri)) {
            return null
        }

        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor.use {
            val results = generateSequence { if (cursor.moveToNext()) cursor else null }.map {
                val id = it.getInt(0)
                when {
                    mimeType.contains("video") -> MediaStore.Video.Thumbnails.getThumbnail(context.contentResolver, id.toLong(), MediaStore.Video.Thumbnails.MINI_KIND, null)
                    mimeType.contains("image/*") -> MediaStore.Images.Thumbnails.getThumbnail(context.contentResolver, id.toLong(), MediaStore.Images.Thumbnails.MINI_KIND, null)
                    else -> null
                }
            }.filter { it != null }.toList()

            return if (results.isNotEmpty()) {
                results[0]
            } else {
                null
            }
        }
    }

        /**
         * check uri is media uri
         * @return True if Uri is a MediaStore Uri.
         */
        private fun isMediaUri(uri: Uri?): Boolean = if (uri != null) {
            "media".equals(uri.authority, ignoreCase = true)
        } else false


        /**
         * Convert File into Uri.
         */
        private fun getUri(file: File?): Uri? {
            return if (file != null) {
                Uri.fromFile(file)
            } else null
        }

        /**
         * @return The MIME type for the given file.
         */
        private fun getMimeType(file: File): String {
            val extension = getExtension(file.name)
            return if (extension.isNotEmpty()) MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1))
            else "application/octet-stream"
        }

        /**
         * Gets the extension of a file name, like ".png" or ".jpg".
         */
        private fun getExtension(uri: String?): String {
            if (uri == null) {
                return ""
            }

            val dot = uri.lastIndexOf(".")
            return if (dot >= 0) {
                uri.substring(dot)
            } else {
                ""
            }
        }

    }