package com.example.irobot.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;

import com.example.irobot.bean.ChatMessage;
import com.example.irobot.bean.ChatMessage.Type;
import com.example.irobot.bean.MusicInfo;
import com.example.irobot.bean.Result;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class HttpUtils {
	// HTTP请求工具类
	private static final String URL = "http://www.tuling123.com/openapi/api";
	private static final String API_KEY = "4d3a0b2449c34ac58066203e5e275cd5";
	private static String song_id;
	private static String songname;
	private static int error_code;
	private static String havehigh;
	static MusicInfo musicinfo;
	/**
	 * 发送消息，得到返回消息
	 * 
	 * @param msg
	 * @return
	 */
	public static ChatMessage sendMessage(String msg) {
		ChatMessage chatMessage = new ChatMessage();
		String jsonRes = doGet(msg);// 返回的json字符串
		Gson gson = new Gson();
		Result result = null;
		try {
			result = gson.fromJson(jsonRes, Result.class);
			Log.i("cn","result="+result);
			chatMessage.setMsg(result.getText());
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			chatMessage.setMsg("服务器繁忙，请稍后再试");
		}
		chatMessage.setDate(new Date());
		chatMessage.setType(Type.INCOMING);
		return chatMessage;

	}

	public static String doGet(String msg) {
		String result = "";
		String url = setParams(msg);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			java.net.URL urlNet = new java.net.URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlNet.openConnection();
			conn.setReadTimeout(5 * 1000);
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			is = conn.getInputStream();
			int len = -1;
			byte[] buf = new byte[128];
			baos = new ByteArrayOutputStream();
			while ((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			baos.flush();
			result = new String(baos.toByteArray());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;

	}

	private static String setParams(String msg) {
		// TODO Auto-generated method stub
		String url = "";
		try {
			url = URL + "?key=" + API_KEY + "&info=" + URLEncoder.encode(msg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
//	Author:AvaLee start
	
	public static MusicInfo sendMusicInfo(String msg) {
		try {
			musicinfo = new MusicInfo();			
			MusicInfoJson infojson = new MusicInfoJson();
			String jsonRes = doBDGet(msg);
			Log.i("ir", "jsonre="+jsonRes);// 返回的json字符串
			if (jsonRes!="none") {
				musicinfo.conn=true;
				Log.i("ir", "jsonRes!=\"none\" musicinfo.conn="+musicinfo.conn);
				error_code =infojson.parse0(jsonRes);
				Log.i("ir", "error_code="+error_code);
						if(error_code==22000) {
							 Bundle songinfo = infojson.parse1(jsonRes);
							 song_id = songinfo.getString("song_id");
							 songname= songinfo.getString("songname");
							 musicinfo.infoexists=(Boolean) false;
							 Log.i("ir","22000"+musicinfo.infoexists);
							 Log.i("ir", "22000 musicinfo.conn="+musicinfo.conn);
							 String jsonRes2 = doBDGet2(msg);
								 if (jsonRes2!="none") {
								 musicinfo.conn=true;
								 Log.i("ir", "jsonRes2!=\"none\" musicinfo.conn="+musicinfo.conn);
								 havehigh = infojson.parse2(jsonRes2);
										 if(havehigh=="2") {
											 musicinfo = infojson.parse3(jsonRes2);
											 musicinfo.song_id = song_id;
											 musicinfo.songname=songname;
											 musicinfo.conn=true;
											 Log.i("ir","2"+musicinfo.infoexists);
											 Log.i("ir", "2 musicinfo.conn="+musicinfo.conn);
										 }else if (havehigh=="0") {
											 musicinfo.infoexists=(Boolean) false;
											 Log.i("ir","0"+musicinfo.infoexists);
											 Log.i("ir", "0 musicinfo.conn="+musicinfo.conn);
										 }
								 
								 }else {
									 musicinfo.conn=false;
									 Log.i("ir", "jsonRes2==\"none\" musicinfo.conn="+musicinfo.conn);
									 return musicinfo;
						
								 }
							 }else if (error_code==22001) {
								 musicinfo.infoexists=(Boolean) false;
								 Log.i("ir","22001"+musicinfo.infoexists);
								 Log.i("ir", "22001 musicinfo.conn="+musicinfo.conn);
							 }		
				 Log.i("ir","return="+musicinfo.infoexists);
				 Log.i("ir", "jsonRes!=\"none\"  return  musicinfo.conn="+musicinfo.conn);
				 return musicinfo;
			}else {
				musicinfo.conn=false;
				Log.i("ir", "jsonRes==\"none\"  musicinfo.conn="+musicinfo.conn);
				return musicinfo;
			}
			
		} catch(Exception e) {
		}
		return null;

	}
	
	public static String doBDGet(String msg) {
		String result = "";
		String url = setBDParams1(msg);
		Log.i("irobot","doBDGet"+url);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			java.net.URL urlNet = new java.net.URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlNet.openConnection();
			conn.setReadTimeout(5 * 1000);
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			if(conn.getResponseCode() == 200) { 	
			is = conn.getInputStream();
			int len = -1;
			byte[] buf = new byte[128];
			baos = new ByteArrayOutputStream();
			while ((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			baos.flush();
			result = new String(baos.toByteArray());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			result="none";
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.i("ir", "doBDGet result="+result);
		return result;
		
	}
	public static String doBDGet2(String msg) {
		String result = "";
		String url2 = setBDParams2(msg);
		Log.i("irobot","doBDGet2"+url2);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			java.net.URL urlNet = new java.net.URL(url2);
			HttpURLConnection conn = (HttpURLConnection) urlNet.openConnection();
			conn.setReadTimeout(5 * 1000);
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			
			if(conn.getResponseCode() == 200) {
				
			is = conn.getInputStream();
			int len = -1;
			byte[] buf = new byte[128];
			baos = new ByteArrayOutputStream();
			while ((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			baos.flush();
			result = new String(baos.toByteArray());
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			result="none";
			e.printStackTrace();
		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.i("ir", "doBDGet2 result="+result);
		return result;
		

	}
	
	
	public static Bitmap getBitmap(String bitmapfilePath) {	
		try {
			String url = "";
			url =musicinfo.pic_small;
			java.net.URL urlNet = new java.net.URL(url);
			
	        HttpURLConnection conn = (HttpURLConnection)urlNet.openConnection();  
	        conn.setConnectTimeout(5000);  
	        conn.setRequestMethod("GET");  
	        if(conn.getResponseCode() == 200){  
		        InputStream inputStream = conn.getInputStream();  
		        Bitmap bitmap = BitmapFactory.decodeStream(inputStream); 		  
		        Log.i("ir","1 http bitmap="+bitmap);
		        // saveBitmapToFile(bitmap, bitmapfilePath);
		        Log.i("ir","2 http bitmap="+bitmap);
		        Log.i("irobot", "save request bitmap success!");
		        return bitmap;
		    }	
		}catch(Exception e) {
			
		}
		return null;
	}
	private static void saveBitmapToFile(Bitmap bitmap, String bitmapfilePath) throws IOException{
		 FileOutputStream fos = new FileOutputStream(new File(bitmapfilePath));
	     bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
	     fos.flush();
	     fos.close();
	    }
	  
//	public static void getMusic(){
//			Log.d("ir","getMusic");
//		 String urlStr=musicinfo.file_link;
//		 
//		 if(urlStr.equals(null)==false) {
//         String musicfilePath=Environment.getExternalStorageDirectory().getPath() +File.separator+ musicinfo.songname+".mp3";
//         Log.d("ir","musicfilePath="+musicfilePath);
//         OutputStream output=null;  
//         try {  
//             /*
//              * 通过URL取得HttpURLConnection
//              * 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
//              * <uses-permission android:name="android.permission.INTERNET" />
//              */  
//             java.net.URL url=new java.net.URL(urlStr);  
//             HttpURLConnection conn=(HttpURLConnection)url.openConnection();  
//             //取得inputStream，并将流中的信息写入SDCard  
//               
//             /*
//              * 写前准备
//              * 1.在AndroidMainfest.xml中进行权限配置
//              * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
//              * 取得写入SDCard的权限
//              * 2.取得SDCard的路径： Environment.getExternalStorageDirectory()
//              * 3.检查要保存的文件上是否已经存在
//              * 4.不存在，新建文件夹，新建文件
//              * 5.将input流中的信息写入SDCard
//              * 6.关闭流
//              */  
//             
//             File file=new File(musicfilePath);  
//             InputStream input=conn.getInputStream();  
//             if(file.exists()){  
//                 Log.d("ir","download file exists!");
//          
//                 return;  
//             }else{  
//                 
//                 file.createNewFile();//新建文件  
//                 output=new FileOutputStream(file);  
//                 //读取大文件  
//                 byte[] buffer=new byte[6*1024];  
//                 int length=0;
//                 while((length=input.read(buffer,0,buffer.length))!=-1){  
//                     output.write(buffer,0,length);  
//                     Log.d("ir","download");
//                 }  
//                 
//                 Log.e("ir","download music end");
//                 output.flush();  
//             } 
//         } catch (MalformedURLException e) {  
//             e.printStackTrace(); 
//             Log.d("ir","1 e="+e);
//         } catch (IOException e) {  
//             e.printStackTrace();  
//             Log.d("ir","2 e="+e);
//         }finally{  
//             try {  
//                     output.close();  
//                     System.out.println("success");  
//                 } catch (Exception e) {  
//                     System.out.println("fail");  
//                     e.printStackTrace();  
//                 }  
//         }
//         }
//		 
//     }  
//	    	        
	 
	
	private static String setBDParams1(String msg) {
		// TODO Auto-generated method stub
		String url = "";
		try {
			url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.6.5.0&method=baidu.ting.search.catalogSug&format=json&query="+ URLEncoder.encode(msg, "UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	@SuppressWarnings("unused")
	private static String setBDParams2(String msg) {
		// TODO Auto-generated method stub
		String url2 = "";
		url2="http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.song.play&songid="+song_id;
		return url2;
	}

//	end
	
	
	
}
