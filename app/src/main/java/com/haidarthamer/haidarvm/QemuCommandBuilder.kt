package com.haidarthamer.haidarvm
import java.io.File
data class VmConfig(val name:String,val ramMb:Int=2048,val cores:Int=4,val disk:File,val iso:File?=null,val vncPort:Int=5900)
object QemuCommandBuilder {
 fun build(qemu:File,c:VmConfig):List<String>{
  val maxCores=Runtime.getRuntime().availableProcessors().coerceAtLeast(2)
  val ram=c.ramMb.coerceIn(1024,4096)
  val cores=c.cores.coerceIn(1,maxCores)
  return listOf(qemu.absolutePath,"-machine","q35","-accel","tcg,thread=multi","-cpu","max","-smp",cores.toString(),
   "-m",ram.toString(),"-nodefaults","-display","none","-vnc","127.0.0.1:"+((c.vncPort-5900).coerceAtLeast(0)),
   "-device","virtio-gpu-pci","-device","virtio-keyboard-pci","-device","virtio-mouse-pci",
   "-drive","file="+c.disk.absolutePath+",if=virtio,format=qcow2","-nic","user,model=virtio").let{
    if(c.iso!=null) it + listOf("-cdrom",c.iso.absolutePath,"-boot","order=d") else it
   }
 }
}