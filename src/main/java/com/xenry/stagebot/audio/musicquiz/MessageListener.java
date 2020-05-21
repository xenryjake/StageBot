package com.xenry.stagebot.audio.musicquiz;
import com.xenry.stagebot.audio.IAudioInstance;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/20/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class MessageListener extends ListenerAdapter {
	
	private final MusicQuizHandler musicQuizHandler;
	
	public MessageListener(MusicQuizHandler musicQuizHandler){
		this.musicQuizHandler = musicQuizHandler;
	}
	
	@Override
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		IAudioInstance iInstance = musicQuizHandler.audioHandler.getInstance(event.getGuild());
		if(!(iInstance instanceof MusicQuizAudioInstance)){
			return;
		}
	}
	
}
