package com.haidarthamer.haidarvm.core

import java.io.File
import java.util.concurrent.atomic.AtomicReference

class QemuRunner {
    private val process = AtomicReference<Process?>(null)

    fun start(config: VmConfig, qemuBinary: File): Result<Unit> = runCatching {
        require(qemuBinary.isFile && qemuBinary.canExecute()) { "QEMU binary is missing or not executable" }
        check(process.get() == null) { "A VM is already running" }

        val command = QemuCommandBuilder.build(config, qemuBinary.absolutePath)
        process.set(ProcessBuilder(command).redirectErrorStream(true).start())
    }

    fun stop(): Result<Unit> = runCatching {
        process.getAndSet(null)?.destroy()
    }

    fun isRunning(): Boolean = process.get()?.isAlive == true
}
