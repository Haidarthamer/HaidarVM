package com.haidarthamer.haidarvm
import android.content.Context;import android.graphics.*;import android.view.*;import java.io.*;import java.net.Socket;import java.nio.ByteBuffer;import java.nio.ByteOrder
class VncView(c:Context,private val host:String,private val port:Int):View(c){
 private var socket:Socket?=null;private var input:DataInputStream?=null;private var output:DataOutputStream?=null
 private var bmp:Bitmap?=null;private var bw=1;private var bh=1
 init{setBackgroundColor(Color.BLACK);Thread{runClient()}.start()}
 private fun u8()=input!!.readUnsignedByte();private fun u16()=input!!.readUnsignedShort();private fun u32()=input!!.readInt()
 private fun runClient(){try{
  socket=Socket(host,port);input=DataInputStream(BufferedInputStream(socket!!.getInputStream()));output=DataOutputStream(BufferedOutputStream(socket!!.getOutputStream()))
  val ver=ByteArray(12);input!!.readFully(ver);output!!.write("RFB 003.008\n".toByteArray());output!!.flush()
  val n=u8();val sec=ByteArray(n);input!!.readFully(sec);output!!.writeByte(if(sec.contains(1))1 else sec[0].toInt());output!!.flush()
  if(u32()!=0)return
  output!!.writeByte(1);output!!.flush()
  bw=u16();bh=u16();input!!.skipBytes(16);bmp=Bitmap.createBitmap(bw,bh,Bitmap.Config.ARGB_8888)
  output!!.writeByte(0);output!!.writeByte(0);output!!.writeShort(0);output!!.writeShort(0);output!!.writeShort(bw);output!!.writeShort(bh);output!!.flush()
  while(true){when(u8()){
   0->{val count=u16();repeat(count){val x=u16();val y=u16();val w=u16();val h=u16();val enc=u32();if(enc!=0){skipRect(w,h);return@repeat};val data=IntArray(w*h);for(i in data.indices){val r=u8();val g=u8();val b=u8();val a=u8();data[i]=Color.argb(255,r,g,b)};bmp?.setPixels(data,0,w,x,y,w,h)};postInvalidate()}
   2->{val count=u8();repeat(count){input!!.skipBytes(3)}}
   3->{input!!.skipBytes(7);output!!.writeByte(3);output!!.writeByte(0);output!!.writeShort(1);output!!.writeShort(0);output!!.writeShort(0);output!!.writeShort(bw);output!!.writeShort(bh);output!!.flush()}
   255->return
   else->return
  }}
 }catch(_:Throwable){}}
 private fun skipRect(w:Int,h:Int){input!!.skipBytes(w*h*4)}
 override fun onDraw(c:Canvas){super.onDraw(c);bmp?.let{val dst=Rect(0,0,width,height);c.drawBitmap(it,null,dst,Paint(Paint.FILTER_BITMAP_FLAG))}}
 override fun onTouchEvent(e:MotionEvent):Boolean{if(e.action==MotionEvent.ACTION_DOWN||e.action==MotionEvent.ACTION_MOVE||e.action==MotionEvent.ACTION_UP){try{val x=(e.x/width*bw).toInt().coerceIn(0,bw-1);val y=(e.y/height*bh).toInt().coerceIn(0,bh-1);val mask=if(e.action==MotionEvent.ACTION_UP)0 else 1;output?.writeByte(5);output?.writeByte(mask);output?.writeShort(x);output?.writeShort(y);output?.flush()}catch(_:Throwable){}};return true}
}