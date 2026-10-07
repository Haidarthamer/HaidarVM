package com.haidarthamer.haidarvm
import android.content.Context
import android.graphics.*
import android.view.*
import java.io.*
import java.net.Socket

class VncView(c:Context,private val host:String,private val port:Int):View(c){
 private var socket:Socket?=null
 private var input:DataInputStream?=null
 private var output:DataOutputStream?=null
 private var bmp:Bitmap?=null
 private var bw=1
 private var bh=1
 init{setBackgroundColor(Color.BLACK);Thread{runClient()}.start()}
 private fun u8()=input!!.readUnsignedByte()
 private fun u16()=input!!.readUnsignedShort()
 private fun i32()=input!!.readInt()
 private fun runClient(){
  try{
   socket=Socket(host,port);input=DataInputStream(BufferedInputStream(socket!!.getInputStream()));output=DataOutputStream(BufferedOutputStream(socket!!.getOutputStream()))
   val ver=ByteArray(12);input!!.readFully(ver);output!!.write("RFB 003.008\n".toByteArray());output!!.flush()
   val n=u8();val sec=ByteArray(n);input!!.readFully(sec)
   val chosen=if(sec.any{it.toInt()==1})1 else sec[0].toInt()
   output!!.writeByte(chosen);output!!.flush();if(i32()!=0)return
   output!!.writeByte(1);output!!.flush()
   bw=u16();bh=u16();input!!.skipBytes(16);bmp=Bitmap.createBitmap(bw,bh,Bitmap.Config.ARGB_8888)
   // Force 32-bit true-color, little-endian, RGBX byte order.
   output!!.writeByte(0);output!!.writeByte(32);output!!.writeByte(24);output!!.writeByte(0);output!!.writeByte(1)
   output!!.writeShort(255);output!!.writeShort(255);output!!.writeShort(255)
   output!!.writeByte(16);output!!.writeByte(8);output!!.writeByte(0);output!!.writeByte(0);output!!.writeByte(0);output!!.writeByte(0);output!!.flush()
   output!!.writeByte(2);output!!.writeByte(0);output!!.writeShort(1);output!!.writeInt(0);output!!.flush()
   requestFull()
   while(true){
    when(u8()){
     0->{val count=u16();repeat(count){
       val x=u16();val y=u16();val w=u16();val h=u16();val enc=i32()
       if(enc!=0){input!!.skipBytes(w*h*4);return@repeat}
       val data=IntArray(w*h)
       for(i in data.indices){val b=u8();val g=u8();val r=u8();u8();data[i]=Color.rgb(r,g,b)}
       bmp?.setPixels(data,0,w,x,y,w,h)
      };postInvalidate();requestFull()}
     2->{val count=u8();repeat(count){input!!.skipBytes(3)}}
     3->{input!!.skipBytes(7);requestFull()}
     else->return
    }
   }
  }catch(_:Throwable){}
 }
 private fun requestFull(){try{output?.writeByte(3);output?.writeByte(0);output?.writeShort(0);output?.writeShort(0);output?.writeShort(bw);output?.writeShort(bh);output?.flush()}catch(_:Throwable){}}
 override fun onDraw(c:Canvas){super.onDraw(c);bmp?.let{c.drawBitmap(it,null,Rect(0,0,width,height),Paint(Paint.FILTER_BITMAP_FLAG))}}
 override fun onTouchEvent(e:MotionEvent):Boolean{
  try{
   val x=(e.x/width*bw).toInt().coerceIn(0,bw-1);val y=(e.y/height*bh).toInt().coerceIn(0,bh-1)
   val mask=if(e.action==MotionEvent.ACTION_UP)0 else 1
   output?.writeByte(5);output?.writeByte(mask);output?.writeShort(x);output?.writeShort(y);output?.flush()
  }catch(_:Throwable){}
  return true
 }
 override fun onDetachedFromWindow(){try{socket?.close()}catch(_:Throwable){};super.onDetachedFromWindow()}
}