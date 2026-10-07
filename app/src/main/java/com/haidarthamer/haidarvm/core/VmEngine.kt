package com.haidarthamer.haidarvm.core

interface VmEngine {
    fun engineName(): String
    fun canStart(config: VmConfig): Boolean
    fun start(config: VmConfig): Result<Unit>
    fun stop(): Result<Unit>
}
