package com.haidarthamer.haidarvm
import android.content.Context
import java.io.File
object Storage {
 fun root(c:Context)=File(c.getExternalFilesDir(null),"vms").apply{mkdirs()}
 fun vmDir(c:Context,name:String)=File(root(c),name).apply{mkdirs()}
 fun disk(c:Context,name:String)=File(vmDir(c,name),"disk.qcow2")
 fun iso(c:Context,name:String)=File(vmDir(c,name),"installer.iso")
 fun engineDir(c:Context)=File(c.filesDir,"qemu").apply{mkdirs()}
}