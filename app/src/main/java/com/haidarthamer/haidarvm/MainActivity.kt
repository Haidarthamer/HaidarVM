package com.haidarthamer.haidarvm

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.*

class MainActivity : Activity() {
    private fun tv(text: String, size: Float = 16f) = TextView(this).apply {
        this.text = text
        textSize = size
        setTextColor(Color.WHITE)
        setPadding(18, 14, 18, 14)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
            setBackgroundColor(Color.rgb(11, 13, 18))
        }
        root.addView(tv("HaidarVM", 30f))
        root.addView(tv("General VM manager • Windows / Linux • no-root-first", 15f))

        val actions = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        fun button(label: String, click: () -> Unit) = Button(this).apply {
            text = label
            setOnClickListener { click() }
        }
        actions.addView(button("Install OS") {
            startActivity(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "application/octet-stream"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
            })
        }, LinearLayout.LayoutParams(0, -2, 1f))
        actions.addView(button("Import VM") {
            startActivity(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "*/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
            })
        }, LinearLayout.LayoutParams(0, -2, 1f))
        root.addView(actions)

        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 18, 18, 18)
            setBackgroundColor(Color.rgb(25, 29, 38))
        }
        card.addView(tv("Create VM", 21f))
        card.addView(tv("Windows 7 / 10 / 11 x86_64\nLinux x86_64 / ARM64\nGPU: experimental until an accelerated guest path is verified"))
        val progress = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
            max = 100
            progress = 0
            visibility = View.GONE
        }
        card.addView(progress, LinearLayout.LayoutParams(-1, 20))
        card.addView(tv("No VM installed yet"))
        root.addView(card, LinearLayout.LayoutParams(-1, -2))

        val caps = com.haidarthamer.haidarvm.core.CapabilityDetector.detect(this)
        root.addView(tv("Host: ${caps.abi}\nKVM: ${caps.kvmPresent}\nGunyah/GVM: ${caps.gunyahPresent}"))

        setContentView(ScrollView(this).apply { addView(root) })
    }
}
