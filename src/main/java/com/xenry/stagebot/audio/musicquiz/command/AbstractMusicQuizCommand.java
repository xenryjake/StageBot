package com.xenry.stagebot.audio.musicquiz.command;
import com.xenry.stagebot.audio.command.AbstractAudioCommand;
import com.xenry.stagebot.audio.musicquiz.MusicQuizHandler;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/20/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public abstract class AbstractMusicQuizCommand extends AbstractAudioCommand {
	
	protected final MusicQuizHandler musicQuizHandler;
	
	public AbstractMusicQuizCommand(MusicQuizHandler musicQuizHandler, String...labels){
		super(musicQuizHandler.audioHandler, labels);
		this.musicQuizHandler = musicQuizHandler;
	}
	
}
