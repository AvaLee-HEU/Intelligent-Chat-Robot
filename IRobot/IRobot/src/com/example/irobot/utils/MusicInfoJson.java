package com.example.irobot.utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.irobot.bean.MusicInfo;

import android.os.Bundle;
import android.util.Log;

public class MusicInfoJson {
	
		public int parse0(String json) throws Exception{
			JSONTokener jsonParser = new JSONTokener(json);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
			int error_code = jsonObject.getInt("error_code");
			return error_code;
		}
	
	
		@SuppressWarnings("unused")
		public Bundle parse1(String json) throws Exception{
			JSONTokener jsonParser = new JSONTokener(json);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
			String error_code = jsonObject.getString("error_code");
			JSONArray resultArray = jsonObject.getJSONArray("song");
			JSONObject MusicObject = resultArray.getJSONObject(0);
			String songname=MusicObject.getString("songname");
			String song_id = MusicObject.getString("songid");
			Bundle songinfo = new Bundle();
			songinfo.putString("song_id", song_id);
			songinfo.putString("songname", songname);
			songinfo.putString("error_code", error_code);
			return songinfo;
		}
		
		
		public String parse2(String json) throws Exception{
			MusicInfo musicInfo = new MusicInfo();
			JSONTokener jsonParser = new JSONTokener(json);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
				JSONObject songinfo = jsonObject.getJSONObject("songinfo");
				musicInfo.havehigh=songinfo.getString("havehigh");
				return musicInfo.havehigh;
				
		}
		
		
		public MusicInfo parse3(String json) throws Exception{
			MusicInfo musicInfo = new MusicInfo();
			JSONTokener jsonParser = new JSONTokener(json);
			JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
				JSONObject songinfo = jsonObject.getJSONObject("songinfo");
				musicInfo.author = songinfo.getString("author");
				musicInfo.title = songinfo.getString("title");
				musicInfo.pic_small = songinfo.getString("pic_small");
				musicInfo.havehigh=songinfo.getString("havehigh");

				JSONObject bitrate = jsonObject.getJSONObject("bitrate");
				musicInfo.file_size= bitrate.getInt("file_size");
				musicInfo.file_extension = bitrate.getString("file_extension");
				musicInfo.file_link =  bitrate.getString("file_link");
				musicInfo.infoexists=(Boolean) true;

				return musicInfo;
				
		}

}
