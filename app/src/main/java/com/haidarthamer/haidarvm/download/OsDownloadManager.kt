package com.haidarthamer.haidarvm.download

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment

class OsDownloadManager(private val context: Context) {
    fun enqueue(url: String, fileName: String, title: String): Long {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(title)
            .setDescription("HaidarVM OS image")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalFilesDir(
                context,
                Environment.DIRECTORY_DOWNLOADS,
                fileName
            )
        return context.getSystemService(DownloadManager::class.java).enqueue(request)
    }

    fun query(id: Long): DownloadStatus? {
        val manager = context.getSystemService(DownloadManager::class.java)
        manager.query(DownloadManager.Query().setFilterById(id)).use { c ->
            if (!c.moveToFirst()) return null
            val status = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS))
            val downloaded = c.getLong(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
            val total = c.getLong(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
            return DownloadStatus(id, status, downloaded, total)
        }
    }
}

data class DownloadStatus(
    val id: Long,
    val status: Int,
    val downloadedBytes: Long,
    val totalBytes: Long
)
