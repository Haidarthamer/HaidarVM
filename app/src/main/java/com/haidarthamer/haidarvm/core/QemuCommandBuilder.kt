package com.haidarthamer.haidarvm.core

object QemuCommandBuilder {
    fun build(config: VmConfig, qemuPath: String): List<String> {
        require(config.architecture == GuestArchitecture.X86_64) {
            "This prototype currently builds x86_64 guest commands only"
        }
        return buildList {
            add(qemuPath)
            add("-machine"); add("q35")
            add("-accel"); add("tcg,thread=multi")
            add("-cpu"); add("max")
            add("-smp"); add(config.cpuCores.toString())
            add("-m"); add(config.ramMb.toString())
            add("-boot"); add("menu=on")
            add("-device"); add("VGA")
            add("-display"); add("vnc=127.0.0.1:0")
            add("-device"); add("virtio-net-pci,netdev=n0")
            add("-netdev"); add("user,id=n0")
            config.diskPath?.let {
                add("-drive"); add("file=$it,if=virtio,format=qcow2")
            }
            config.isoPath?.let {
                add("-drive"); add("file=$it,media=cdrom,if=ide,readonly=on")
            }
        }
    }
}
