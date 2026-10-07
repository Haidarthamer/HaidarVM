package com.haidarthamer.haidarvm
import android.app.*;import android.content.*;import android.os.*;import java.io.*
class DownloadService:Service(){
 override fun onBind(i:Intent?)=null
 override fun onStartCommand(i:Intent?,flags:Int,startId:Int):Int{
  val url=i?.getStringExtra("url")?:return START_NOT_STICKY
  val out=i.getStringExtra("out")?:return START_NOT_STICKY
  val channel="haidarvm_downloads"
  getSystemService(NotificationManager::class.java).createNotificationChannel(NotificationChannel(channel,"Downloads",NotificationManager.IMPORTANCE_LOW))
  startForeground(7,Notification.Builder(this,channel).setContentTitle("HaidarVM").setContentText("Downloading VM files").setSmallIcon(android.R.drawable.stat_sys_download).setOngoing(true).build())
  Thread{
   try{
    val f=File(out);f.parentFile?.mkdirs()
    val conn=(java.net.URL(url).openConnection() as java.net.HttpURLConnection)
    if(f.exists())conn.setRequestProperty("Range","bytes="+f.length()+"-")
    conn.connect()
    val append=conn.responseCode==206
    conn.inputStream.use{input->FileOutputStream(f,append).use{output->val b=ByteArray(1024*1024);var n:Int;while(input.read(b).also{n=it}>0)output.write(b,0,n)}}
   }catch(_:Throwable){}finally{stopForeground(STOP_FOREGROUND_REMOVE);stopSelf(startId)}
  }.start()
  return START_STICKY
 }
}