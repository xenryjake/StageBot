package com.xenry.stagebot.audio.musicquiz;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class MusicQuizSong extends BasicDBObject {
	
	public MusicQuizSong(){
		//required for Mongo instantiation
	}
	
	public MusicQuizSong(String url, String title, String...artists){
		put("url", url);
		put("title", title);
		put("artists", Arrays.asList(artists));
	}
	
	public String getURL(){
		return getString("url");
	}
	
	public String getTitle(){
		return getString("title");
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getArtists(){
		return (List<String>)get("artists");
	}
	
}
