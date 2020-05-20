package com.xenry.stagebot.audio.musicquiz;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.audio.AudioPlayerSendHandler;
import com.xenry.stagebot.audio.IAudioInstance;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class MusicQuizInstance extends AudioEventAdapter implements IAudioInstance {
	
	private final MessageChannel messageChannel;
	private final VoiceChannel voiceChannel;
	private final AudioHandler handler;
	private final AudioPlayer player;
	private final ArrayList<AudioTrack> queue;
	
	public MusicQuizInstance(AudioHandler handler, MessageChannel messageChannel, VoiceChannel voiceChannel){
		this.handler = handler;
		this.messageChannel = messageChannel;
		this.voiceChannel = voiceChannel;
		
		queue = new ArrayList<>();
		
		player = handler.manager.createPlayer();
		player.addListener(this);
	}
	
	@Override
	public boolean connect() {
		AudioManager audioManager = voiceChannel.getGuild().getAudioManager();
		try{
			audioManager.openAudioConnection(voiceChannel);
			audioManager.setSendingHandler(new AudioPlayerSendHandler(player));
		}catch(PermissionException ex){
			return false;
		}
		return true;
	}
	
	@Override
	public void disconnect() {
		voiceChannel.getGuild().getAudioManager().closeAudioConnection();
	}
	
	@Override
	public boolean isConnected() {
		return voiceChannel.getGuild().getAudioManager().isConnected();
	}
	
	@Override
	public MessageChannel getMessageChannel() {
		return messageChannel;
	}
	
	@Override
	public VoiceChannel getVoiceChannel() {
		return voiceChannel;
	}
	
	@Override
	public AudioPlayer getPlayer() {
		return player;
	}
	
	
	
}
