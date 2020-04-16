package me.sonaive.lab.util

import android.content.Intent
import android.net.Uri
import android.os.Build
import java.io.*
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

/**
 * Created by liutao on 2020-02-28.
 * Describe: 文件工具方法
 */

/**
 * Return the file by path.
 *
 * @param filePath The path of file.
 * @return the file
 */
fun getFileByPath(filePath: String): File? {
    return if (isSpace(filePath)) null else File(filePath)
}

/**
 * Return whether the file exists.
 *
 * @param filePath The path of file.
 * @return `true`: yes<br></br>`false`: no
 */
fun isFileExists(filePath: String): Boolean {
    if (Build.VERSION.SDK_INT < 29) {
        return isFileExists(getFileByPath(filePath))
    } else {
        try {
            val uri = Uri.parse(filePath)
            val cr = AppUtil.appContext.contentResolver
            val afd = cr.openAssetFileDescriptor(uri, "r") ?: return false
            try {
                afd.close()
            } catch (ignore: IOException) {
            }

        } catch (e: FileNotFoundException) {
            return false
        }

        return true
    }
}

/**
 * Return whether the file exists.
 *
 * @param file The file.
 * @return `true`: yes<br></br>`false`: no
 */
fun isFileExists(file: File?): Boolean {
    return file != null && file.exists()
}

/**
 * Rename the file.
 *
 * @param filePath The path of file.
 * @param newName  The new name of file.
 * @return `true`: success<br></br>`false`: fail
 */
fun rename(filePath: String, newName: String): Boolean {
    return rename(getFileByPath(filePath), newName)
}

/**
 * Rename the file.
 *
 * @param file    The file.
 * @param newName The new name of file.
 * @return `true`: success<br></br>`false`: fail
 */
fun rename(file: File?, newName: String): Boolean {
    // file is null then return false
    if (file == null) return false
    // file doesn't exist then return false
    if (!file.exists()) return false
    // the new name is space then return false
    if (isSpace(newName)) return false
    // the new name equals old name then return true
    if (newName == file.name) return true
    val newFile = File(file.parent + File.separator + newName)
    // the new name of file exists then return false
    return !newFile.exists() && file.renameTo(newFile)
}

/**
 * Return whether it is a directory.
 *
 * @param dirPath The path of directory.
 * @return `true`: yes<br></br>`false`: no
 */
fun isDir(dirPath: String): Boolean {
    return isDir(getFileByPath(dirPath))
}

/**
 * Return whether it is a directory.
 *
 * @param file The file.
 * @return `true`: yes<br></br>`false`: no
 */
fun isDir(file: File?): Boolean {
    return file != null && file.exists() && file.isDirectory
}

/**
 * Return whether it is a file.
 *
 * @param filePath The path of file.
 * @return `true`: yes<br></br>`false`: no
 */
fun isFile(filePath: String): Boolean {
    return isFile(getFileByPath(filePath))
}

/**
 * Return whether it is a file.
 *
 * @param file The file.
 * @return `true`: yes<br></br>`false`: no
 */
fun isFile(file: File?): Boolean {
    return file != null && file.exists() && file.isFile
}

/**
 * Create a directory if it doesn't exist, otherwise do nothing.
 *
 * @param dirPath The path of directory.
 * @return `true`: exists or creates successfully<br></br>`false`: otherwise
 */
fun createOrExistsDir(dirPath: String): Boolean {
    return createOrExistsDir(getFileByPath(dirPath))
}

/**
 * Create a directory if it doesn't exist, otherwise do nothing.
 *
 * @param file The file.
 * @return `true`: exists or creates successfully<br></br>`false`: otherwise
 */
fun createOrExistsDir(file: File?): Boolean {
    return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
}

/**
 * Create a file if it doesn't exist, otherwise do nothing.
 *
 * @param filePath The path of file.
 * @return `true`: exists or creates successfully<br></br>`false`: otherwise
 */
fun createOrExistsFile(filePath: String): Boolean {
    return createOrExistsFile(getFileByPath(filePath))
}

/**
 * Create a file if it doesn't exist, otherwise do nothing.
 *
 * @param file The file.
 * @return `true`: exists or creates successfully<br></br>`false`: otherwise
 */
fun createOrExistsFile(file: File?): Boolean {
    if (file == null) return false
    if (file.exists()) return file.isFile
    if (!createOrExistsDir(file.parentFile)) return false
    try {
        return file.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }

}

/**
 * Create a file if it doesn't exist, otherwise delete old file before creating.
 *
 * @param filePath The path of file.
 * @return `true`: success<br></br>`false`: fail
 */
fun createFileByDeleteOldFile(filePath: String): Boolean {
    return createFileByDeleteOldFile(getFileByPath(filePath))
}

/**
 * Create a file if it doesn't exist, otherwise delete old file before creating.
 *
 * @param file The file.
 * @return `true`: success<br></br>`false`: fail
 */
fun createFileByDeleteOldFile(file: File?): Boolean {
    if (file == null) return false
    // file exists and unsuccessfully delete then return false
    if (file.exists() && !file.delete()) return false
    if (!createOrExistsDir(file.parentFile)) return false
    try {
        return file.createNewFile()
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }

}

/**
 * Copy the directory or file.
 *
 * @param srcPath  The path of source.
 * @param destPath The path of destination.
 * @return `true`: success<br></br>`false`: fail
 */
fun copy(
    srcPath: String,
    destPath: String
): Boolean {
    return copy(getFileByPath(srcPath), getFileByPath(destPath), null)
}

/**
 * Copy the directory or file.
 *
 * @param srcPath  The path of source.
 * @param destPath The path of destination.
 * @param listener The replace listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun copy(
    srcPath: String,
    destPath: String,
    listener: OnReplaceListener
): Boolean {
    return copy(getFileByPath(srcPath), getFileByPath(destPath), listener)
}

/**
 * Copy the directory or file.
 *
 * @param src  The source.
 * @param dest The destination.
 * @return `true`: success<br></br>`false`: fail
 */
fun copy(
    src: File,
    dest: File
): Boolean {
    return copy(src, dest, null)
}

/**
 * Copy the directory or file.
 *
 * @param src      The source.
 * @param dest     The destination.
 * @param listener The replace listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun copy(
    src: File?,
    dest: File?,
    listener: OnReplaceListener?
): Boolean {
    if (src == null) return false
    return if (src.isDirectory) {
        copyDir(src, dest, listener)
    } else copyFile(src, dest, listener)
}

/**
 * Copy the directory.
 *
 * @param srcDir   The source directory.
 * @param destDir  The destination directory.
 * @param listener The replace listener.
 * @return `true`: success<br></br>`false`: fail
 */
private fun copyDir(
    srcDir: File,
    destDir: File?,
    listener: OnReplaceListener?
): Boolean {
    return copyOrMoveDir(srcDir, destDir, listener, false)
}

/**
 * Copy the file.
 *
 * @param srcFile  The source file.
 * @param destFile The destination file.
 * @param listener The replace listener.
 * @return `true`: success<br></br>`false`: fail
 */
private fun copyFile(
    srcFile: File,
    destFile: File?,
    listener: OnReplaceListener?
): Boolean {
    return copyOrMoveFile(srcFile, destFile, listener, false)
}

/**
 * Move the directory or file.
 *
 * @param srcPath  The path of source.
 * @param destPath The path of destination.
 * @return `true`: success<br></br>`false`: fail
 */
fun move(
    srcPath: String,
    destPath: String
): Boolean {
    return move(getFileByPath(srcPath), getFileByPath(destPath), null)
}

/**
 * Move the directory or file.
 *
 * @param srcPath  The path of source.
 * @param destPath The path of destination.
 * @param listener The replace listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun move(
    srcPath: String,
    destPath: String,
    listener: OnReplaceListener
): Boolean {
    return move(getFileByPath(srcPath), getFileByPath(destPath), listener)
}

/**
 * Move the directory or file.
 *
 * @param src  The source.
 * @param dest The destination.
 * @return `true`: success<br></br>`false`: fail
 */
fun move(
    src: File,
    dest: File
): Boolean {
    return move(src, dest, null)
}

/**
 * Move the directory or file.
 *
 * @param src      The source.
 * @param dest     The destination.
 * @param listener The replace listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun move(
    src: File?,
    dest: File?,
    listener: OnReplaceListener?
): Boolean {
    if (src == null) return false
    return if (src.isDirectory) {
        moveDir(src, dest, listener)
    } else moveFile(src, dest, listener)
}

/**
 * Move the directory.
 *
 * @param srcDir   The source directory.
 * @param destDir  The destination directory.
 * @param listener The replace listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun moveDir(
    srcDir: File,
    destDir: File?,
    listener: OnReplaceListener?
): Boolean {
    return copyOrMoveDir(srcDir, destDir, listener, true)
}

/**
 * Move the file.
 *
 * @param srcFile  The source file.
 * @param destFile The destination file.
 * @param listener The replace listener.
 * @return `true`: success<br></br>`false`: fail
 */
fun moveFile(
    srcFile: File,
    destFile: File?,
    listener: OnReplaceListener?
): Boolean {
    return copyOrMoveFile(srcFile, destFile, listener, true)
}

private fun copyOrMoveDir(
    srcDir: File?,
    destDir: File?,
    listener: OnReplaceListener?,
    isMove: Boolean
): Boolean {
    if (srcDir == null || destDir == null) return false
    // destDir's path locate in srcDir's path then return false
    val srcPath = srcDir.path + File.separator
    val destPath = destDir.path + File.separator
    if (destPath.contains(srcPath)) return false
    if (!srcDir.exists() || !srcDir.isDirectory) return false
    if (!createOrExistsDir(destDir)) return false
    val files = srcDir.listFiles()
    for (file in files!!) {
        val oneDestFile = File(destPath + file.name)
        if (file.isFile) {
            if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) return false
        } else if (file.isDirectory) {
            if (!copyOrMoveDir(file, oneDestFile, listener, isMove)) return false
        }
    }
    return !isMove || deleteDir(srcDir)
}

private fun copyOrMoveFile(
    srcFile: File?,
    destFile: File?,
    listener: OnReplaceListener?,
    isMove: Boolean
): Boolean {
    if (srcFile == null || destFile == null) return false
    // srcFile equals destFile then return false
    if (srcFile == destFile) return false
    // srcFile doesn't exist or isn't a file then return false
    if (!srcFile.exists() || !srcFile.isFile) return false
    if (destFile.exists()) {
        if (listener == null || listener.onReplace(
                srcFile,
                destFile
            )
        ) {// require delete the old file
            if (!destFile.delete()) {// unsuccessfully delete then return false
                return false
            }
        } else {
            return true
        }
    }
    if (!createOrExistsDir(destFile.parentFile)) return false
    try {
        return writeFileFromIS(destFile, FileInputStream(srcFile)) && !(isMove && !deleteFile(
            srcFile
        ))
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        return false
    }

}

/**
 * Delete the directory.
 *
 * @param filePath The path of file.
 * @return `true`: success<br></br>`false`: fail
 */
fun delete(filePath: String): Boolean {
    return delete(getFileByPath(filePath))
}

/**
 * Delete the directory.
 *
 * @param file The file.
 * @return `true`: success<br></br>`false`: fail
 */
fun delete(file: File?): Boolean {
    if (file == null) return false
    return if (file.isDirectory) {
        deleteDir(file)
    } else deleteFile(file)
}

/**
 * Delete the directory.
 *
 * @param dir The directory.
 * @return `true`: success<br></br>`false`: fail
 */
private fun deleteDir(dir: File?): Boolean {
    if (dir == null) return false
    // dir doesn't exist then return true
    if (!dir.exists()) return true
    // dir isn't a directory then return false
    if (!dir.isDirectory) return false
    val files = dir.listFiles()
    if (files != null && files.size != 0) {
        for (file in files) {
            if (file.isFile) {
                if (!file.delete()) return false
            } else if (file.isDirectory) {
                if (!deleteDir(file)) return false
            }
        }
    }
    return dir.delete()
}

/**
 * Delete the file.
 *
 * @param file The file.
 * @return `true`: success<br></br>`false`: fail
 */
private fun deleteFile(file: File?): Boolean {
    return file != null && (!file.exists() || file.isFile && file.delete())
}

/**
 * Delete the all in directory.
 *
 * @param dirPath The path of directory.
 * @return `true`: success<br></br>`false`: fail
 */
fun deleteAllInDir(dirPath: String): Boolean {
    return deleteAllInDir(getFileByPath(dirPath))
}

/**
 * Delete the all in directory.
 *
 * @param dir The directory.
 * @return `true`: success<br></br>`false`: fail
 */
fun deleteAllInDir(dir: File?): Boolean {
    return deleteFilesInDirWithFilter(dir, FileFilter { true })
}

/**
 * Delete all files in directory.
 *
 * @param dirPath The path of directory.
 * @return `true`: success<br></br>`false`: fail
 */
fun deleteFilesInDir(dirPath: String): Boolean {
    return deleteFilesInDir(getFileByPath(dirPath))
}

/**
 * Delete all files in directory.
 *
 * @param dir The directory.
 * @return `true`: success<br></br>`false`: fail
 */
fun deleteFilesInDir(dir: File?): Boolean {
    return deleteFilesInDirWithFilter(dir, FileFilter { pathname -> pathname.isFile })
}

/**
 * Delete all files that satisfy the filter in directory.
 *
 * @param dirPath The path of directory.
 * @param filter  The filter.
 * @return `true`: success<br></br>`false`: fail
 */
fun deleteFilesInDirWithFilter(
    dirPath: String,
    filter: FileFilter
): Boolean {
    return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter)
}

/**
 * Delete all files that satisfy the filter in directory.
 *
 * @param dir    The directory.
 * @param filter The filter.
 * @return `true`: success<br></br>`false`: fail
 */
fun deleteFilesInDirWithFilter(dir: File?, filter: FileFilter?): Boolean {
    if (dir == null || filter == null) return false
    // dir doesn't exist then return true
    if (!dir.exists()) return true
    // dir isn't a directory then return false
    if (!dir.isDirectory) return false
    val files = dir.listFiles()
    if (files != null && files.size != 0) {
        for (file in files) {
            if (filter.accept(file)) {
                if (file.isFile) {
                    if (!file.delete()) return false
                } else if (file.isDirectory) {
                    if (!deleteDir(file)) return false
                }
            }
        }
    }
    return true
}

/**
 * Return the files in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dirPath The path of directory.
 * @return the files in directory
 */
fun listFilesInDir(dirPath: String): List<File> {
    return listFilesInDir(dirPath, null)
}

/**
 * Return the files in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dir The directory.
 * @return the files in directory
 */
fun listFilesInDir(dir: File): List<File> {
    return listFilesInDir(dir, null)
}

/**
 * Return the files in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dirPath    The path of directory.
 * @param comparator The comparator to determine the order of the list.
 * @return the files in directory
 */
fun listFilesInDir(dirPath: String, comparator: Comparator<File>?): List<File> {
    return listFilesInDir(getFileByPath(dirPath), false)
}

/**
 * Return the files in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dir        The directory.
 * @param comparator The comparator to determine the order of the list.
 * @return the files in directory
 */
fun listFilesInDir(dir: File, comparator: Comparator<File>?): List<File> {
    return listFilesInDir(dir, false, comparator)
}

/**
 * Return the files in directory.
 *
 * @param dirPath     The path of directory.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @return the files in directory
 */
fun listFilesInDir(dirPath: String, isRecursive: Boolean): List<File> {
    return listFilesInDir(getFileByPath(dirPath), isRecursive)
}

/**
 * Return the files in directory.
 *
 * @param dir         The directory.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @return the files in directory
 */
fun listFilesInDir(dir: File?, isRecursive: Boolean): List<File> {
    return listFilesInDir(dir, isRecursive, null)
}

/**
 * Return the files in directory.
 *
 * @param dirPath     The path of directory.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @param comparator  The comparator to determine the order of the list.
 * @return the files in directory
 */
fun listFilesInDir(
    dirPath: String,
    isRecursive: Boolean,
    comparator: Comparator<File>
): List<File> {
    return listFilesInDir(getFileByPath(dirPath), isRecursive, comparator)
}

/**
 * Return the files in directory.
 *
 * @param dir         The directory.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @param comparator  The comparator to determine the order of the list.
 * @return the files in directory
 */
fun listFilesInDir(
    dir: File?,
    isRecursive: Boolean,
    comparator: Comparator<File>?
): List<File> {
    return listFilesInDirWithFilter(dir, FileFilter { true }, isRecursive, comparator)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dirPath The path of directory.
 * @param filter  The filter.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dirPath: String,
    filter: FileFilter
): List<File> {
    return listFilesInDirWithFilter(getFileByPath(dirPath), filter)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dir    The directory.
 * @param filter The filter.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dir: File?,
    filter: FileFilter
): List<File> {
    return listFilesInDirWithFilter(dir, filter, false, null)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dirPath    The path of directory.
 * @param filter     The filter.
 * @param comparator The comparator to determine the order of the list.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dirPath: String,
    filter: FileFilter,
    comparator: Comparator<File>
): List<File> {
    return listFilesInDirWithFilter(getFileByPath(dirPath), filter, comparator)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * Doesn't traverse subdirectories
 *
 * @param dir        The directory.
 * @param filter     The filter.
 * @param comparator The comparator to determine the order of the list.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dir: File?,
    filter: FileFilter,
    comparator: Comparator<File>
): List<File> {
    return listFilesInDirWithFilter(dir, filter, false, comparator)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * @param dirPath     The path of directory.
 * @param filter      The filter.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dirPath: String,
    filter: FileFilter,
    isRecursive: Boolean
): List<File> {
    return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * @param dir         The directory.
 * @param filter      The filter.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dir: File?,
    filter: FileFilter,
    isRecursive: Boolean
): List<File> {
    return listFilesInDirWithFilter(dir, filter, isRecursive, null)
}


/**
 * Return the files that satisfy the filter in directory.
 *
 * @param dirPath     The path of directory.
 * @param filter      The filter.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @param comparator  The comparator to determine the order of the list.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dirPath: String,
    filter: FileFilter,
    isRecursive: Boolean,
    comparator: Comparator<File>
): List<File> {
    return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive, comparator)
}

/**
 * Return the files that satisfy the filter in directory.
 *
 * @param dir         The directory.
 * @param filter      The filter.
 * @param isRecursive True to traverse subdirectories, false otherwise.
 * @param comparator  The comparator to determine the order of the list.
 * @return the files that satisfy the filter in directory
 */
fun listFilesInDirWithFilter(
    dir: File?,
    filter: FileFilter,
    isRecursive: Boolean,
    comparator: Comparator<File>?
): List<File> {
    val files = listFilesInDirWithFilterInner(dir, filter, isRecursive)
    if (comparator != null) {
        Collections.sort(files, comparator)
    }
    return files
}

private fun listFilesInDirWithFilterInner(
    dir: File?,
    filter: FileFilter,
    isRecursive: Boolean
): List<File> {
    val list = ArrayList<File>()
    if (!isDir(dir)) return list
    val files = dir!!.listFiles()
    if (files != null && files.size != 0) {
        for (file in files) {
            if (filter.accept(file)) {
                list.add(file)
            }
            if (isRecursive && file.isDirectory) {
                list.addAll(listFilesInDirWithFilterInner(file, filter, true))
            }
        }
    }
    return list
}

/**
 * Return the time that the file was last modified.
 *
 * @param filePath The path of file.
 * @return the time that the file was last modified
 */

fun getFileLastModified(filePath: String): Long {
    return getFileLastModified(getFileByPath(filePath))
}

/**
 * Return the time that the file was last modified.
 *
 * @param file The file.
 * @return the time that the file was last modified
 */
fun getFileLastModified(file: File?): Long {
    return file?.lastModified() ?: -1
}

/**
 * Return the size.
 *
 * @param filePath The path of file.
 * @return the size
 */
fun getSize(filePath: String): String {
    return getSize(getFileByPath(filePath))
}

/**
 * Return the size.
 *
 * @param file The directory.
 * @return the size
 */
fun getSize(file: File?): String {
    if (file == null) return ""
    return if (file.isDirectory) {
        getDirSize(file)
    } else getFileSize(file)
}

/**
 * Return the size of directory.
 *
 * @param dir The directory.
 * @return the size of directory
 */
private fun getDirSize(dir: File): String {
    val len = getDirLength(dir)
    return if (len == -1L) "" else byte2FitMemorySize(len)
}

/**
 * Return the size of file.
 *
 * @param file The file.
 * @return the length of file
 */
private fun getFileSize(file: File): String {
    val len = getFileLength(file)
    return if (len == -1L) "" else byte2FitMemorySize(len)
}

/**
 * Return the length.
 *
 * @param filePath The path of file.
 * @return the length
 */
fun getLength(filePath: String): Long {
    return getLength(getFileByPath(filePath))
}

/**
 * Return the length.
 *
 * @param file The file.
 * @return the length
 */
fun getLength(file: File?): Long {
    if (file == null) return 0
    return if (file.isDirectory) {
        getDirLength(file)
    } else getFileLength(file)
}

/**
 * Return the length of directory.
 *
 * @param dir The directory.
 * @return the length of directory
 */
private fun getDirLength(dir: File): Long {
    if (!isDir(dir)) return -1
    var len: Long = 0
    val files = dir.listFiles()
    if (files != null && files.size != 0) {
        for (file in files) {
            if (file.isDirectory) {
                len += getDirLength(file)
            } else {
                len += file.length()
            }
        }
    }
    return len
}

/**
 * Return the length of file.
 *
 * @param filePath The path of file.
 * @return the length of file
 */
fun getFileLength(filePath: String): Long {
    val isURL = filePath.matches("[a-zA-z]+://[^\\s]*".toRegex())
    if (isURL) {
        try {
            val conn = URL(filePath).openConnection() as HttpsURLConnection
            conn.setRequestProperty("Accept-Encoding", "identity")
            conn.connect()
            return if (conn.responseCode == 200) {
                conn.contentLength.toLong()
            } else -1
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    return getFileLength(getFileByPath(filePath))
}

/**
 * Return the length of file.
 *
 * @param file The file.
 * @return the length of file
 */
private fun getFileLength(file: File?): Long {
    return if (!isFile(file)) -1 else file!!.length()
}

/**
 * Return the file's path of directory.
 *
 * @param file The file.
 * @return the file's path of directory
 */
fun getDirName(file: File?): String {
    return if (file == null) "" else getDirName(file.absolutePath)
}

/**
 * Return the file's path of directory.
 *
 * @param filePath The path of file.
 * @return the file's path of directory
 */
fun getDirName(filePath: String): String {
    if (isSpace(filePath)) return ""
    val lastSep = filePath.lastIndexOf(File.separator)
    return if (lastSep == -1) "" else filePath.substring(0, lastSep + 1)
}

/**
 * Return the name of file.
 *
 * @param file The file.
 * @return the name of file
 */
fun getFileName(file: File?): String {
    return if (file == null) "" else getFileName(file.absolutePath)
}

/**
 * Return the name of file.
 *
 * @param filePath The path of file.
 * @return the name of file
 */
fun getFileName(filePath: String): String {
    if (isSpace(filePath)) return ""
    val lastSep = filePath.lastIndexOf(File.separator)
    return if (lastSep == -1) filePath else filePath.substring(lastSep + 1)
}

/**
 * Return the name of file without extension.
 *
 * @param file The file.
 * @return the name of file without extension
 */
fun getFileNameNoExtension(file: File?): String {
    return if (file == null) "" else getFileNameNoExtension(file.path)
}

/**
 * Return the name of file without extension.
 *
 * @param filePath The path of file.
 * @return the name of file without extension
 */
fun getFileNameNoExtension(filePath: String): String {
    if (isSpace(filePath)) return ""
    val lastPoi = filePath.lastIndexOf('.')
    val lastSep = filePath.lastIndexOf(File.separator)
    if (lastSep == -1) {
        return if (lastPoi == -1) filePath else filePath.substring(0, lastPoi)
    }
    return if (lastPoi == -1 || lastSep > lastPoi) {
        filePath.substring(lastSep + 1)
    } else filePath.substring(lastSep + 1, lastPoi)
}

/**
 * Return the extension of file.
 *
 * @param file The file.
 * @return the extension of file
 */
fun getFileExtension(file: File?): String {
    return if (file == null) "" else getFileExtension(file.path)
}

/**
 * Return the extension of file.
 *
 * @param filePath The path of file.
 * @return the extension of file
 */
fun getFileExtension(filePath: String): String {
    if (isSpace(filePath)) return ""
    val lastPoi = filePath.lastIndexOf('.')
    val lastSep = filePath.lastIndexOf(File.separator)
    return if (lastPoi == -1 || lastSep >= lastPoi) "" else filePath.substring(lastPoi + 1)
}

/**
 * Notify system to scan the file.
 *
 * @param file The file.
 */
fun notifySystemToScan(file: File?) {
    if (file == null || !file.exists()) return
    val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    val uri = Uri.fromFile(file)
    intent.data = uri
    AppUtil.appContext.sendBroadcast(intent)
}

/**
 * Notify system to scan the file.
 *
 * @param filePath The path of file.
 */
fun notifySystemToScan(filePath: String) {
    notifySystemToScan(getFileByPath(filePath))
}

///////////////////////////////////////////////////////////////////////////
// interface
///////////////////////////////////////////////////////////////////////////

interface OnReplaceListener {
    fun onReplace(srcFile: File, destFile: File): Boolean
}

///////////////////////////////////////////////////////////////////////////
// other utils methods
///////////////////////////////////////////////////////////////////////////

private fun byte2FitMemorySize(byteNum: Long): String {
    return if (byteNum < 0) {
        String.format(Locale.getDefault(), "%.3fB", 0)
    } else if (byteNum < 1024) {
        String.format(Locale.getDefault(), "%.3fB", byteNum.toDouble())
    } else if (byteNum < 1048576) {
        String.format(Locale.getDefault(), "%.3fKB", byteNum.toDouble() / 1024)
    } else if (byteNum < 1073741824) {
        String.format(Locale.getDefault(), "%.3fMB", byteNum.toDouble() / 1048576)
    } else {
        String.format(Locale.getDefault(), "%.3fGB", byteNum.toDouble() / 1073741824)
    }
}

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

private fun writeFileFromIS(
    file: File,
    inputStream: InputStream
): Boolean {
    var os: OutputStream? = null
    try {
        os = BufferedOutputStream(FileOutputStream(file))
        val data = ByteArray(8192)
        var len: Int
        do {
            len = inputStream.read(data, 0, 8192)
            if (len != -1) {
                os.write(data, 0, len)
            }
        } while (len != -1)
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