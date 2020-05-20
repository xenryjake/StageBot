package com.xenry.stagebot.audio;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public interface IAudioInstance {
	
	boolean connect();
	
	void disconnect();
	
	boolean isConnected();
	
	MessageChannel getMessageChannel();
	
	VoiceChannel getVoiceChannel();
	
	AudioPlayer getPlayer();
	
}
