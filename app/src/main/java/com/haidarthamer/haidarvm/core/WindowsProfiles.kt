package com.haidarthamer.haidarvm.core

object WindowsProfiles {
    fun windows7(iso: String, disk: String) = VmConfig(
        name = "Windows 7",
        architecture = GuestArchitecture.X86_64,
        ramMb = 4096,
        cpuCores = 4,
        diskPath = disk,
        isoPath = iso
    )

    fun windows10(iso: String, disk: String) = VmConfig(
        name = "Windows 10",
        architecture = GuestArchitecture.X86_64,
        ramMb = 6144,
        cpuCores = 6,
        diskPath = disk,
        isoPath = iso
    )

    fun windows11(iso: String, disk: String) = VmConfig(
        name = "Windows 11",
        architecture = GuestArchitecture.X86_64,
        ramMb = 8192,
        cpuCores = 8,
        diskPath = disk,
        isoPath = iso
    )
}
