package com.haidarthamer.haidarvm
import android.app.*;import android.os.*;import android.content.*;import android.graphics.Color;import android.view.*;import android.widget.*
class MainActivity:Activity(){
 private lateinit var status:TextView
 private fun t(s:String)=TextView(this).apply{text=s;textSize=16f;setTextColor(Color.WHITE);setPadding(8,8,8,8)}
 override fun onCreate(b:Bundle?){super.onCreate(b)
  val root=LinearLayout(this);root.orientation=LinearLayout.VERTICAL;root.setPadding(20,20,20,20)
  root.setBackgroundColor(Color.rgb(11,13,18));root.addView(t("HaidarVM",28f))
  status=t("No-root TCG • QEMU x86_64 • Scoped VM storage");root.addView(status)
  val install=Button(this).apply{text="Install / Download OS"};root.addView(install)
  val imp=Button(this).apply{text="Import ISO / QCOW2"};root.addView(imp)
  val start=Button(this).apply{text="Start Windows x86_64"};root.addView(start)
  install.setOnClickListener{status.text="Use the downloader/engine manager to fetch QEMU and an OS image in the background."}
  imp.setOnClickListener{startActivity(Intent(Intent.ACTION_OPEN_DOCUMENT).setType("*/*").addCategory(Intent.CATEGORY_OPENABLE))}
  start.setOnClickListener{
   val disk=Storage.disk(this,"Windows");val iso=Storage.iso(this,"Windows")
   if(!disk.exists()){status.text="Windows disk missing. Import/create a QCOW2 first.";return@setOnClickListener}
   try{QemuEngine(this).start(VmConfig("Windows",2048,4,disk,iso.takeIf{it.exists()}));status.text="VM started. Opening VNC…";Handler(Looper.getMainLooper()).postDelayed({setContentView(VncView(this,"127.0.0.1",5900))},800)}
   catch(e:Exception){status.text=e.message?:"VM start failed"}
  }
  setContentView(root)
 }
}