package me.sonaive.lab.util

import java.io.*
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import java.util.ArrayList

/**
 * Created by liutao on 2020-02-28.
 * Describe: 文件流操作工具方法
 */
private var bufferSize = 8192

///////////////////////////////////////////////////////////////////////////
// writeFileFromIS without progress
///////////////////////////////////////////////////////////////////////////

/**
 * Write file from input stream.
 *
 * @param filePath The path of file.
 * @param inputStream The input stream.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromIS(filePath: String, inputStream: InputStream): Boolean {
    return writeFileFromIS(getFileByPath(filePath), inputStream, false, null)
}

/**
 * Write file from input stream.
 *
 * @param filePath The path of file.
 * @param inputStream The input stream.
 * @param append   True to append, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromIS(
    filePath: String,
    inputStream: InputStream,
    append: Boolean
): Boolean {
    return writeFileFromIS(getFileByPath(filePath), inputStream, append, null)
}

/**
 * Write file from input stream.
 *
 * @param file   The file.
 * @param inputStream The input stream.
 * @param append True to append, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromIS(
    file: File,
    inputStream: InputStream,
    append: Boolean
): Boolean {
    return writeFileFromIS(file, inputStream, append, null)
}

///////////////////////////////////////////////////////////////////////////
// writeFileFromIS with progress
///////////////////////////////////////////////////////////////////////////

/**
 * Write file from input stream.
 *
 * @param filePath The path of file.
 * @param inputStream The input stream.
 * @param listener The progress update listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromIS(
    filePath: String,
    inputStream: InputStream,
    listener: OnProgressUpdateListener
): Boolean {
    return writeFileFromIS(getFileByPath(filePath), inputStream, false, listener)
}

/**
 * Write file from input stream.
 *
 * @param filePath The path of file.
 * @param inputStream The input stream.
 * @param append   True to append, false otherwise.
 * @param listener The progress update listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromIS(
    filePath: String,
    inputStream: InputStream,
    append: Boolean,
    listener: OnProgressUpdateListener
): Boolean {
    return writeFileFromIS(getFileByPath(filePath), inputStream, append, listener)
}

/**
 * Write file from input stream.
 *
 * @param file     The file.
 * @param inputStream The input stream.
 * @param listener The progress update listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromIS(
    file: File,
    inputStream: InputStream,
    listener: OnProgressUpdateListener
): Boolean {
    return writeFileFromIS(file, inputStream, false, listener)
}

/**
 * Write file from input stream.
 *
 * @param file     The file.
 * @param inputStream The input stream.
 * @param append   True to append, false otherwise.
 * @param listener The progress update listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromIS(
    file: File?,
    inputStream: InputStream?,
    append: Boolean,
    listener: OnProgressUpdateListener?
): Boolean {
    if (inputStream == null || !createOrExistsFile(file)) return false
    var os: OutputStream? = null
    try {
        os = BufferedOutputStream(FileOutputStream(file!!, append), bufferSize)
        if (listener == null) {
            val data = ByteArray(bufferSize)
            var len: Int
            do {
                len = inputStream.read(data)
                if (len != -1) {
                    os.write(data, 0, len)
                }
            } while (len != -1)
        } else {
            val totalSize = inputStream.available().toDouble()
            var curSize = 0
            listener.onProgressUpdate(0.0)
            val data = ByteArray(bufferSize)
            var len: Int
            do {
                len = inputStream.read(data)
                if (len != -1) {
                    os.write(data, 0, len)
                    curSize += len
                    listener.onProgressUpdate(curSize / totalSize)
                }
            } while (len != -1)
        }
        return true
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    } finally {
        try {
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            os?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}


///////////////////////////////////////////////////////////////////////////
// writeFileFromBytesByStream without progress
///////////////////////////////////////////////////////////////////////////

/**
 * Write file from bytes by stream.
 *
 * @param filePath The path of file.
 * @param bytes    The bytes.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByStream(filePath: String, bytes: ByteArray): Boolean {
    return writeFileFromBytesByStream(getFileByPath(filePath), bytes, false, null)
}

/**
 * Write file from bytes by stream.
 *
 * @param filePath The path of file.
 * @param bytes    The bytes.
 * @param append   True to append, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByStream(
    filePath: String,
    bytes: ByteArray,
    append: Boolean
): Boolean {
    return writeFileFromBytesByStream(getFileByPath(filePath), bytes, append, null)
}

/**
 * Write file from bytes by stream.
 *
 * @param file  The file.
 * @param bytes The bytes.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByStream(file: File, bytes: ByteArray): Boolean {
    return writeFileFromBytesByStream(file, bytes, false, null)
}

/**
 * Write file from bytes by stream.
 *
 * @param file   The file.
 * @param bytes  The bytes.
 * @param append True to append, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByStream(
    file: File,
    bytes: ByteArray,
    append: Boolean
): Boolean {
    return writeFileFromBytesByStream(file, bytes, append, null)
}

///////////////////////////////////////////////////////////////////////////
// writeFileFromBytesByStream with progress
///////////////////////////////////////////////////////////////////////////

/**
 * Write file from bytes by stream.
 *
 * @param filePath The path of file.
 * @param bytes    The bytes.
 * @param listener The progress update listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByStream(
    filePath: String,
    bytes: ByteArray,
    listener: OnProgressUpdateListener
): Boolean {
    return writeFileFromBytesByStream(getFileByPath(filePath), bytes, false, listener)
}

/**
 * Write file from bytes by stream.
 *
 * @param filePath The path of file.
 * @param bytes    The bytes.
 * @param append   True to append, false otherwise.
 * @param listener The progress update listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByStream(
    filePath: String,
    bytes: ByteArray,
    append: Boolean,
    listener: OnProgressUpdateListener
): Boolean {
    return writeFileFromBytesByStream(getFileByPath(filePath), bytes, append, listener)
}

/**
 * Write file from bytes by stream.
 *
 * @param file     The file.
 * @param bytes    The bytes.
 * @param listener The progress update listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByStream(
    file: File,
    bytes: ByteArray,
    listener: OnProgressUpdateListener
): Boolean {
    return writeFileFromBytesByStream(file, bytes, false, listener)
}

/**
 * Write file from bytes by stream.
 *
 * @param file     The file.
 * @param bytes    The bytes.
 * @param append   True to append, false otherwise.
 * @param listener The progress update listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByStream(
    file: File?,
    bytes: ByteArray?,
    append: Boolean,
    listener: OnProgressUpdateListener?
): Boolean {
    return if (bytes == null) false else writeFileFromIS(
        file,
        ByteArrayInputStream(bytes), append, listener
    )
}

/**
 * Write file from bytes by channel.
 *
 * @param filePath The path of file.
 * @param bytes    The bytes.
 * @param isForce  是否写入文件
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByChannel(
    filePath: String,
    bytes: ByteArray,
    isForce: Boolean
): Boolean {
    return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, false, isForce)
}

/**
 * Write file from bytes by channel.
 *
 * @param filePath The path of file.
 * @param bytes    The bytes.
 * @param append   True to append, false otherwise.
 * @param isForce  True to force write file, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByChannel(
    filePath: String,
    bytes: ByteArray,
    append: Boolean,
    isForce: Boolean
): Boolean {
    return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, append, isForce)
}

/**
 * Write file from bytes by channel.
 *
 * @param file    The file.
 * @param bytes   The bytes.
 * @param isForce True to force write file, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByChannel(
    file: File,
    bytes: ByteArray,
    isForce: Boolean
): Boolean {
    return writeFileFromBytesByChannel(file, bytes, false, isForce)
}

/**
 * Write file from bytes by channel.
 *
 * @param file    The file.
 * @param bytes   The bytes.
 * @param append  True to append, false otherwise.
 * @param isForce True to force write file, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByChannel(
    file: File?,
    bytes: ByteArray?,
    append: Boolean,
    isForce: Boolean
): Boolean {
    if (bytes == null || !createOrExistsFile(file)) return false
    var fc: FileChannel? = null
    try {
        fc = FileOutputStream(file!!, append).channel
        fc!!.position(fc.size())
        fc.write(ByteBuffer.wrap(bytes))
        if (isForce) fc.force(true)
        return true
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    } finally {
        try {
            fc?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}

/**
 * Write file from bytes by map.
 *
 * @param filePath The path of file.
 * @param bytes    The bytes.
 * @param isForce  True to force write file, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByMap(
    filePath: String,
    bytes: ByteArray,
    isForce: Boolean
): Boolean {
    return writeFileFromBytesByMap(filePath, bytes, false, isForce)
}

/**
 * Write file from bytes by map.
 *
 * @param filePath The path of file.
 * @param bytes    The bytes.
 * @param append   True to append, false otherwise.
 * @param isForce  True to force write file, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByMap(
    filePath: String,
    bytes: ByteArray,
    append: Boolean,
    isForce: Boolean
): Boolean {
    return writeFileFromBytesByMap(getFileByPath(filePath), bytes, append, isForce)
}

/**
 * Write file from bytes by map.
 *
 * @param file    The file.
 * @param bytes   The bytes.
 * @param isForce True to force write file, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByMap(
    file: File,
    bytes: ByteArray,
    isForce: Boolean
): Boolean {
    return writeFileFromBytesByMap(file, bytes, false, isForce)
}

/**
 * Write file from bytes by map.
 *
 * @param file    The file.
 * @param bytes   The bytes.
 * @param append  True to append, false otherwise.
 * @param isForce True to force write file, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromBytesByMap(
    file: File?,
    bytes: ByteArray?,
    append: Boolean,
    isForce: Boolean
): Boolean {
    if (bytes == null || !createOrExistsFile(file)) return false
    var fc: FileChannel? = null
    try {
        fc = FileOutputStream(file!!, append).channel
        val mbb = fc!!.map(FileChannel.MapMode.READ_WRITE, fc.size(), bytes.size.toLong())
        mbb.put(bytes)
        if (isForce) mbb.force()
        return true
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    } finally {
        try {
            fc?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}

/**
 * Write file from string.
 *
 * @param filePath The path of file.
 * @param content  The string of content.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromString(filePath: String, content: String): Boolean {
    return writeFileFromString(getFileByPath(filePath), content, false)
}

/**
 * Write file from string.
 *
 * @param filePath The path of file.
 * @param content  The string of content.
 * @param append   True to append, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromString(
    filePath: String,
    content: String,
    append: Boolean
): Boolean {
    return writeFileFromString(getFileByPath(filePath), content, append)
}

/**
 * Write file from string.
 *
 * @param file    The file.
 * @param content The string of content.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromString(file: File, content: String): Boolean {
    return writeFileFromString(file, content, false)
}

/**
 * Write file from string.
 *
 * @param file    The file.
 * @param content The string of content.
 * @param append  True to append, false otherwise.
 * @return `true`: success<br></br>`false`: fail
 */
fun writeFileFromString(
    file: File?,
    content: String?,
    append: Boolean
): Boolean {
    if (file == null || content == null) return false
    if (!createOrExistsFile(file)) return false
    var bw: BufferedWriter? = null
    try {
        bw = BufferedWriter(FileWriter(file, append))
        bw.write(content)
        return true
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    } finally {
        try {
            bw?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}

///////////////////////////////////////////////////////////////////////////
// the divide line of write and read
///////////////////////////////////////////////////////////////////////////

/**
 * Return the lines in file.
 *
 * @param filePath The path of file.
 * @return the lines in file
 */
fun readFile2List(filePath: String): List<String>? {
    return readFile2List(getFileByPath(filePath), null)
}

/**
 * Return the lines in file.
 *
 * @param filePath    The path of file.
 * @param charsetName The name of charset.
 * @return the lines in file
 */
fun readFile2List(filePath: String, charsetName: String): List<String>? {
    return readFile2List(getFileByPath(filePath), charsetName)
}

/**
 * Return the lines in file.
 *
 * @param file The file.
 * @return the lines in file
 */
fun readFile2List(file: File): List<String>? {
    return readFile2List(file, 0, 0x7FFFFFFF, null)
}

/**
 * Return the lines in file.
 *
 * @param file        The file.
 * @param charsetName The name of charset.
 * @return the lines in file
 */
fun readFile2List(file: File?, charsetName: String?): List<String>? {
    return readFile2List(file, 0, 0x7FFFFFFF, charsetName)
}

/**
 * Return the lines in file.
 *
 * @param filePath The path of file.
 * @param st       The line's index of start.
 * @param end      The line's index of end.
 * @return the lines in file
 */
fun readFile2List(filePath: String, st: Int, end: Int): List<String>? {
    return readFile2List(getFileByPath(filePath), st, end, null)
}

/**
 * Return the lines in file.
 *
 * @param filePath    The path of file.
 * @param st          The line's index of start.
 * @param end         The line's index of end.
 * @param charsetName The name of charset.
 * @return the lines in file
 */
fun readFile2List(
    filePath: String,
    st: Int,
    end: Int,
    charsetName: String
): List<String>? {
    return readFile2List(getFileByPath(filePath), st, end, charsetName)
}

/**
 * Return the lines in file.
 *
 * @param file The file.
 * @param st   The line's index of start.
 * @param end  The line's index of end.
 * @return the lines in file
 */
fun readFile2List(file: File, st: Int, end: Int): List<String>? {
    return readFile2List(file, st, end, null)
}

/**
 * Return the lines in file.
 *
 * @param file        The file.
 * @param st          The line's index of start.
 * @param end         The line's index of end.
 * @param charsetName The name of charset.
 * @return the lines in file
 */
fun readFile2List(
    file: File?,
    st: Int,
    end: Int,
    charsetName: String?
): List<String>? {
    if (!isFileExists(file)) return null
    if (st > end) return null
    var reader: BufferedReader? = null
    try {
        var line: String
        var curLine = 1
        val list = ArrayList<String>()
        if (isSpace(charsetName)) {
            reader = BufferedReader(InputStreamReader(FileInputStream(file!!)))
        } else {
            reader = BufferedReader(
                InputStreamReader(FileInputStream(file!!), charsetName!!)
            )
        }
        do {
            line = reader.readLine()
            if (line != null) {
                if (curLine > end) break
                if (st <= curLine && curLine <= end) {
                    list.add(line)
                }
                ++curLine
            }
        } while (line != null)
        return list
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    } finally {
        try {
            reader?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}

/**
 * Return the string in file.
 *
 * @param filePath The path of file.
 * @return the string in file
 */
fun readFile2String(filePath: String): String? {
    return readFile2String(getFileByPath(filePath), null)
}

/**
 * Return the string in file.
 *
 * @param filePath    The path of file.
 * @param charsetName The name of charset.
 * @return the string in file
 */
fun readFile2String(filePath: String, charsetName: String): String? {
    return readFile2String(getFileByPath(filePath), charsetName)
}

/**
 * Return the string in file.
 *
 * @param file The file.
 * @return the string in file
 */
fun readFile2String(file: File): String? {
    return readFile2String(file, null)
}

/**
 * Return the string in file.
 *
 * @param file        The file.
 * @param charsetName The name of charset.
 * @return the string in file
 */
fun readFile2String(file: File?, charsetName: String?): String? {
    val bytes = readFile2BytesByStream(file) ?: return null
    return if (isSpace(charsetName)) {
        String(bytes)
    } else {
        try {
            String(bytes, Charset.forName(charsetName))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            ""
        }

    }
}

///////////////////////////////////////////////////////////////////////////
// readFile2BytesByStream without progress
///////////////////////////////////////////////////////////////////////////

/**
 * Return the bytes in file by stream.
 *
 * @param filePath The path of file.
 * @return the bytes in file
 */
fun readFile2BytesByStream(filePath: String): ByteArray? {
    return readFile2BytesByStream(getFileByPath(filePath), null)
}

/**
 * Return the bytes in file by stream.
 *
 * @param file The file.
 * @return the bytes in file
 */
fun readFile2BytesByStream(file: File?): ByteArray? {
    return readFile2BytesByStream(file, null)
}

///////////////////////////////////////////////////////////////////////////
// readFile2BytesByStream with progress
///////////////////////////////////////////////////////////////////////////

/**
 * Return the bytes in file by stream.
 *
 * @param filePath The path of file.
 * @param listener The progress update listener.
 * @return the bytes in file
 */
fun readFile2BytesByStream(
    filePath: String,
    listener: OnProgressUpdateListener
): ByteArray? {
    return readFile2BytesByStream(getFileByPath(filePath))
}

/**
 * Return the bytes in file by stream.
 *
 * @param file     The file.
 * @param listener The progress update listener.
 * @return the bytes in file
 */
fun readFile2BytesByStream(
    file: File?,
    listener: OnProgressUpdateListener?
): ByteArray? {
    if (!isFileExists(file)) return null
    try {
        var os: ByteArrayOutputStream? = null
        val inputStream = BufferedInputStream(FileInputStream(file!!),
            bufferSize
        )
        try {
            os = ByteArrayOutputStream()
            val b = ByteArray(bufferSize)
            var len: Int
            if (listener == null) {
                do {
                    len = inputStream.read(b, 0, bufferSize)
                    if (len != -1) {
                        os.write(b, 0, len)
                    }
                } while (len != -1)
            } else {
                val totalSize = inputStream.available().toDouble()
                var curSize = 0
                listener.onProgressUpdate(0.0)
                do {
                    len = inputStream.read(b, 0, bufferSize)
                    if (len != -1) {
                        os.write(b, 0, len)
                        curSize += len
                        listener.onProgressUpdate(curSize / totalSize)
                    }
                } while (len != -1)
            }
            return os.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return null
    }

}

/**
 * Return the bytes in file by channel.
 *
 * @param filePath The path of file.
 * @return the bytes in file
 */
fun readFile2BytesByChannel(filePath: String): ByteArray? {
    return readFile2BytesByChannel(getFileByPath(filePath))
}

/**
 * Return the bytes in file by channel.
 *
 * @param file The file.
 * @return the bytes in file
 */
fun readFile2BytesByChannel(file: File?): ByteArray? {
    if (!isFileExists(file)) return null
    var fc: FileChannel? = null
    try {
        fc = RandomAccessFile(file!!, "r").channel
        val byteBuffer = ByteBuffer.allocate(fc!!.size().toInt())
        while (true) {
            if (fc.read(byteBuffer) <= 0) break
        }
        return byteBuffer.array()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    } finally {
        try {
            fc?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}

/**
 * Return the bytes in file by map.
 *
 * @param filePath The path of file.
 * @return the bytes in file
 */
fun readFile2BytesByMap(filePath: String): ByteArray? {
    return readFile2BytesByMap(getFileByPath(filePath))
}

/**
 * Return the bytes in file by map.
 *
 * @param file The file.
 * @return the bytes in file
 */
fun readFile2BytesByMap(file: File?): ByteArray? {
    if (!isFileExists(file)) return null
    var fc: FileChannel? = null
    try {
        fc = RandomAccessFile(file!!, "r").channel
        val size = fc!!.size().toInt()
        val mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size.toLong()).load()
        val result = ByteArray(size)
        mbb.get(result, 0, size)
        return result
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    } finally {
        try {
            fc?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}

/**
 * Set the buffer's size.
 *
 * Default size equals 8192 bytes.
 *
 * @param bufferSize The buffer's size.
 */
fun setBufferSize(bufferSize: Int) {
    me.sonaive.lab.util.bufferSize = bufferSize
}

///////////////////////////////////////////////////////////////////////////
// other utils methods
///////////////////////////////////////////////////////////////////////////

private fun isSpace(s: String?): Boolean {
    if (s == null) return true
    var i = 0
    val len = s.length
    while (i < len) {
        if (!Character.isWhitespace(s[i])) {
            return false
        }
        ++i
    }
    return true
}

private fun is2Bytes(inputStream: InputStream?): ByteArray? {
    if (inputStream == null) return null
    var os: ByteArrayOutputStream? = null
    try {
        os = ByteArrayOutputStream()
        val b = ByteArray(bufferSize)
        var len: Int
        do {
            len = inputStream.read(b, 0, bufferSize)
            if (len != -1) {
                os.write(b, 0, len)
            }
        } while (len != -1)
        return os.toByteArray()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    } finally {
        try {
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            os?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}

interface OnProgressUpdateListener {

    fun onProgressUpdate(progress: Double)

}