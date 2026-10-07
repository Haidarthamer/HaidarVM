package com.haidarthamer.haidarvm.core

import android.content.Context
import android.opengl.GLES20

data class GpuCapability(
    val renderer: String,
    val vendor: String,
    val openGlEs: String
)

object GpuDetector {
    fun detect(context: Context): GpuCapability {
        return GpuCapability(
            renderer = GLES20.glGetString(GLES20.GL_RENDERER) ?: "unknown",
            vendor = GLES20.glGetString(GLES20.GL_VENDOR) ?: "unknown",
            openGlEs = GLES20.glGetString(GLES20.GL_VERSION) ?: "unknown"
        )
    }
}
