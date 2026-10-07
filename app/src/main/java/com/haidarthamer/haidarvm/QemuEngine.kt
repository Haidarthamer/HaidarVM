package com.haidarthamer.haidarvm
import android.content.Context
import java.io.File
class QemuEngine(private val context:Context){
 fun executable():File=File(Storage.engineDir(context),"qemu-system-x86_64")
 fun installBinary(source:File):File{ val dst=executable(); source.copyTo(dst,true); dst.setExecutable(true,false); return dst }
 fun start(config:VmConfig):Process{
  val q=executable(); require(q.exists()){"QEMU engine not installed"}
  require(q.canExecute() || q.setExecutable(true,false)){"QEMU binary is not executable"}
  return ProcessBuilder(QemuCommandBuilder.build(q,config)).redirectErrorStream(true).start()
 }
}