package com.haidarthamer.haidarvm.core

import android.content.Context
import android.os.Build
import java.io.File

data class HostCapabilities(
    val abi: String,
    val supportedAbis: List<String>,
    val kvmPresent: Boolean,
    val gunyahPresent: Boolean
)

object CapabilityDetector {
    fun detect(context: Context): HostCapabilities {
        val abis = Build.SUPPORTED_ABIS.toList()
        return HostCapabilities(
            abi = Build.SUPPORTED_ABIS.firstOrNull() ?: "unknown",
            supportedAbis = abis,
            kvmPresent = File("/dev/kvm").exists(),
            gunyahPresent = File("/dev/gunyah").exists() || File("/dev/gzvm").exists()
        )
    }
}
