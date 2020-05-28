package com.xenry.stagebot.audio.musicquiz;
import com.mongodb.BasicDBObject;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class MusicQuizSong extends BasicDBObject {
	
	public AudioTrack track = null;
	
	@Deprecated
	public MusicQuizSong(){
		//required for Mongo instantiation
	}
	
	public MusicQuizSong(int songID, String url, String title, String artist, int startMS, String validTitle, List<String> validArtists){
		this(songID, url, title, artist, startMS, Collections.singletonList(validTitle), validArtists);
	}
	
	public MusicQuizSong(int songID, String url, String title, String artist, int startMS, List<String> validTitles, List<String> validArtists){
		put("songID", songID);
		put("url", url);
		put("title", title);
		put("artist", artist);
		put("startMS", startMS);
		put("validTitles", validTitles);
		put("validArtists", validArtists);
	}
	
	public int getSongID(){
		return getInt("songID");
	}
	
	public String getURL(){
		return getString("url");
	}
	
	public String getTitle(){
		return getString("title");
	}
	
	public String getArtist(){
		return getString("artist");
	}
	
	public int getStartMS(){
		return getInt("startMS");
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getValidTitles(){
		return (List<String>)get("validTitles");
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getValidArtists(){
		return (List<String>)get("validArtists");
	}
	
}
