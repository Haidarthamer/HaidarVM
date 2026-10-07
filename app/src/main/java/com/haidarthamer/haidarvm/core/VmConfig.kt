package com.haidarthamer.haidarvm.core

enum class GuestArchitecture { X86_64, ARM64 }

data class VmConfig(
    val name: String,
    val architecture: GuestArchitecture,
    val ramMb: Int = 4096,
    val cpuCores: Int = 4,
    val diskPath: String? = null,
    val isoPath: String? = null,
    val gpuAccelerationRequested: Boolean = true
)
