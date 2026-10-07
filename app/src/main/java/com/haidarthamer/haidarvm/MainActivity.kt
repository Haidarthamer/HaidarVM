package com.haidarthamer.haidarvm

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(28, 36, 28, 28)
            setBackgroundColor(Color.rgb(11, 13, 18))
        }
        val title = TextView(this).apply {
            text = "HaidarVM"
            textSize = 30f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER_VERTICAL
        }
        val subtitle = TextView(this).apply {
            text = "General VM manager • no-root-first"
            textSize = 15f
            setTextColor(Color.LTGRAY)
            setPadding(0, 8, 0, 32)
        }
        val status = TextView(this).apply {
            text = "Prototype ready\n\nNext: capability detection → QEMU engine → x86_64 boot → GPU test"
            textSize = 17f
            setTextColor(Color.WHITE)
            setPadding(20, 24, 20, 24)
            setBackgroundColor(Color.rgb(25, 29, 38))
        }
        root.addView(title)
        root.addView(subtitle)
        root.addView(status, LinearLayout.LayoutParams(-1, -2))
        setContentView(root)
    }
}
