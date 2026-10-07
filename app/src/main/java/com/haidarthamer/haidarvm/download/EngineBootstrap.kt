package com.haidarthamer.haidarvm.download

import android.content.Context
import com.haidarthamer.haidarvm.core.EnginePaths
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.zip.GZIPInputStream

/**
 * Downloads an Android-arm64 QEMU bundle. The bundle is deliberately kept
 * external to the APK so the APK stays small and the engine can be replaced
 * independently.
 */
class EngineBootstrap(private val context: Context) {
    // QEMU bundle used by the Android VM ecosystem. Replace with a project-owned
    // signed release once HaidarVM's native build pipeline is producing it.
    private val bundleUrl =
        "https://github.com/AnBui2004/Vectras-VM-Emu-Android/releases/download/4.0.8/base-vectras-vm-arm64-v8a.tar.gz"

    fun download(target: File = File(EnginePaths.root(context), "qemu-bundle.tar.gz")): File {
        target.parentFile?.mkdirs()
        val c = URL(bundleUrl).openConnection() as HttpURLConnection
        c.connectTimeout = 20_000
        c.readTimeout = 60_000
        c.instanceFollowRedirects = true
        c.connect()
        c.inputStream.use { input -> target.outputStream().use { input.copyTo(it) } }
        c.disconnect()
        return target
    }

    fun gunzip(input: File, output: File) {
        GZIPInputStream(input.inputStream().buffered()).use { zin ->
            output.outputStream().use { zout -> zin.copyTo(zout) }
        }
    }
}
