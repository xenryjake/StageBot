package com.xenry.stagebot.audio.musicquiz;
import com.mongodb.DBCollection;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.audio.musicquiz.command.MusicQuizCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/20/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class MusicQuizHandler {
	
	public final AudioHandler audioHandler;
	private final DBCollection songCollection;
	private final List<MusicQuizSong> songs;
	public final MessageListener messageListener;
	
	public MusicQuizHandler(AudioHandler audioHandler){
		this.audioHandler = audioHandler;
		songs = new ArrayList<>();
		songCollection = audioHandler.stageBot.getMongoHandler().getCollection("profiles");
		songCollection.setObjectClass(MusicQuizSong.class);
		
		messageListener = new MessageListener(this);
		audioHandler.stageBot.jda.addEventListener(messageListener);
		
		audioHandler.stageBot.getCommandHandler().register(new MusicQuizCommand(this));
	}
	
}
