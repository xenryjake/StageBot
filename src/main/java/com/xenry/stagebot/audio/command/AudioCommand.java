package com.xenry.stagebot.audio.command;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.command.Command;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public abstract class AudioCommand extends Command {
	
	protected final AudioHandler handler;
	
	protected AudioCommand(AudioHandler handler, String...labels){
		super(handler.stageBot, labels);
		this.handler = handler;
	}
	
}
