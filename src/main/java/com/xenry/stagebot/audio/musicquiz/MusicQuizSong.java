package com.xenry.stagebot.audio.musicquiz;
/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public enum MusicQuizSong {
	
	;
	
	private final String url;
	private final String name;
	private final String artist;
	
	MusicQuizSong(String url, String name, String artist) {
		this.url = url;
		this.name = name;
		this.artist = artist;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getName() {
		return name;
	}
	
	public String getArtist() {
		return artist;
	}
	
}
