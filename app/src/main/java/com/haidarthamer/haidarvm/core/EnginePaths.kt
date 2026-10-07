package com.haidarthamer.haidarvm.core

import android.content.Context
import java.io.File

object EnginePaths {
    fun root(context: Context) = File(context.filesDir, "engine").apply { mkdirs() }
    fun qemu(context: Context) = File(root(context), "qemu-system-x86_64")
    fun firmware(context: Context) = File(root(context), "firmware")
}
