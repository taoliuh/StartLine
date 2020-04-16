package me.sonaive.lab.image

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.os.Build
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.PAINT_FLAGS
import com.bumptech.glide.util.Synthetic
import java.security.MessageDigest
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * Created by liutao on 2020-03-27.
 * Describe: 参考Glide TransformationUtils实现的图片底部多余区域裁剪的实现，
 * 实现了Glide BitmapTransformation接口，使用了内置的图片缓存池获取更好性能。
 */
class FitTop: BitmapTransformation() {

    private val ID = "me.sonaive.lab.image.load.resource.bitmap.FitTop"
    private val ID_BYTES = ID.toByteArray(Key.CHARSET)

    // See #738.
    private val MODELS_REQUIRING_BITMAP_LOCK = HashSet(
        Arrays.asList(
            // Moto X gen 2
            "XT1085",
            "XT1092",
            "XT1093",
            "XT1094",
            "XT1095",
            "XT1096",
            "XT1097",
            "XT1098",
            // Moto G gen 1
            "XT1031",
            "XT1028",
            "XT937C",
            "XT1032",
            "XT1008",
            "XT1033",
            "XT1035",
            "XT1034",
            "XT939G",
            "XT1039",
            "XT1040",
            "XT1042",
            "XT1045",
            // Moto G gen 2
            "XT1063",
            "XT1064",
            "XT1068",
            "XT1069",
            "XT1072",
            "XT1077",
            "XT1078",
            "XT1079"
        )
    )

    /**
     * https://github.com/bumptech/glide/issues/738 On some devices, bitmap drawing is not thread
     * safe.
     * This lock only locks for these specific devices. For other types of devices the lock is always
     * available and therefore does not impact performance
     */
    private val BITMAP_DRAWABLE_LOCK = if (MODELS_REQUIRING_BITMAP_LOCK.contains(Build.MODEL))
        ReentrantLock()
    else
        NoLock()

    private val DEFAULT_PAINT = Paint(PAINT_FLAGS)


    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        return fitTop(pool,toTransform, outWidth, outHeight)
    }

    fun fitTop(
        pool: BitmapPool, inBitmap: Bitmap, width: Int,
        height: Int
    ): Bitmap {
        if (inBitmap.width == width && inBitmap.height == height) {
            return inBitmap
        }
        // From ImageView/Bitmap.createScaledBitmap.
        val scale: Float
        val m = Matrix()
        if (inBitmap.width * height > width * inBitmap.height) {
            scale = height.toFloat() / inBitmap.height.toFloat()
        } else {
            scale = width.toFloat() / inBitmap.width.toFloat()
        }
        m.setScale(scale, scale)

        val result = pool.get(width, height, getNonNullConfig(inBitmap))
        // We don't add or remove alpha, so keep the alpha setting of the Bitmap we were given.
        TransformationUtils.setAlpha(inBitmap, result)

        applyMatrix(inBitmap, result, m)
        return result
    }

    private fun getNonNullConfig(bitmap: Bitmap): Bitmap.Config {
        return if (bitmap.config != null) bitmap.config else Bitmap.Config.ARGB_8888
    }

    private fun applyMatrix(
        inBitmap: Bitmap, targetBitmap: Bitmap,
        matrix: Matrix
    ) {
        BITMAP_DRAWABLE_LOCK.lock()
        try {
            val canvas = Canvas(targetBitmap)
            canvas.drawBitmap(inBitmap, matrix, DEFAULT_PAINT)
            clear(canvas)
        } finally {
            BITMAP_DRAWABLE_LOCK.unlock()
        }
    }

    // Avoids warnings in M+.
    private fun clear(canvas: Canvas) {
        canvas.setBitmap(null)
    }

    private class NoLock @Synthetic internal constructor() : Lock {

        override fun lock() {
            // do nothing
        }

        @Throws(InterruptedException::class)
        override fun lockInterruptibly() {
            // do nothing
        }

        override fun tryLock(): Boolean {
            return true
        }

        @Throws(InterruptedException::class)
        override fun tryLock(time: Long, unit: TimeUnit): Boolean {
            return true
        }

        override fun unlock() {
            // do nothing
        }

        override fun newCondition(): Condition {
            throw UnsupportedOperationException("Should not be called")
        }
    }

}