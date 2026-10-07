package com.haidarthamer.haidarvm.download

import java.io.File
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream

object TarExtractor {
    fun extractGzTar(gz: File, destination: File) {
        destination.mkdirs()
        java.util.zip.GZIPInputStream(gz.inputStream().buffered()).use { gzip ->
            TarArchiveInputStream(gzip).use { tar ->
                while (true) {
                    val entry = tar.nextTarEntry ?: break
                    val safe = File(destination, entry.name).canonicalFile
                    require(safe.path.startsWith(destination.canonicalPath + File.separator)) {
                        "Unsafe archive path"
                    }
                    if (entry.isDirectory) {
                        safe.mkdirs()
                    } else {
                        safe.parentFile?.mkdirs()
                        safe.outputStream().use { out -> tar.copyTo(out) }
                        safe.setExecutable(true, false)
                    }
                }
            }
        }
    }

    fun findExecutable(root: File, name: String): File? =
        root.walkTopDown().firstOrNull { it.isFile && it.name == name }
}
