package com.xenry.stagebot.audio;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.xenry.stagebot.util.Log;
import com.xenry.stagebot.util.MessageUtil;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class AudioInstance extends AudioEventAdapter {
	
	private final MessageChannel messageChannel;
	private final VoiceChannel voiceChannel;
	private final AudioHandler handler;
	private final AudioPlayer player;
	private final ArrayList<AudioTrack> queue;
	
	AudioInstance(AudioHandler handler, MessageChannel messageChannel, VoiceChannel voiceChannel) {
		this.handler = handler;
		this.messageChannel = messageChannel;
		this.voiceChannel = voiceChannel;
		
		queue = new ArrayList<>();
		
		player = handler.manager.createPlayer();
		player.addListener(this);
	}
	
	public boolean connect(){
		AudioManager audioManager = voiceChannel.getGuild().getAudioManager();
		try{
			audioManager.openAudioConnection(voiceChannel);
			audioManager.setSendingHandler(new AudioPlayerSendHandler(player));
		}catch(PermissionException ex){
			return false;
		}
		return true;
	}
	
	public void disconnect(){
		voiceChannel.getGuild().getAudioManager().closeAudioConnection();
	}
	
	public boolean isConnected(){
		return voiceChannel.getGuild().getAudioManager().isConnected();
	}
	
	public MessageChannel getMessageChannel() {
		return messageChannel;
	}
	
	public VoiceChannel getVoiceChannel() {
		return voiceChannel;
	}
	
	public AudioPlayer getPlayer() {
		return player;
	}
	
	public ArrayList<AudioTrack> getQueue() {
		return queue;
	}
	
	public void addToQueue(AudioTrack track){
		queue.add(track);
	}
	
	public void playNext(){
		if(!player.isPaused()){
			player.stopTrack();
		}
		if(queue.size() < 1){
			return;
		}
		player.playTrack(queue.get(0));
	}
	
	public void shuffleQueue(){
		List<AudioTrack> tQueue = new ArrayList<>(queue);
		AudioTrack current = tQueue.get(0);
		tQueue.remove(0);
		Collections.shuffle(tQueue);
		tQueue.add(0, current);
		queue.clear();
		queue.addAll(tQueue);
	}
	
	public void clearQueue(){
		queue.clear();
	}
	
	public void loadTrack(String identifier){
		handler.manager.loadItem(identifier, new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack track) {
				Log.info("Track loaded");
				addToQueue(track);
				player.startTrack(track, true);
				MessageUtil.sendMessage(messageChannel, ":white_check_mark: Added `" + track.getInfo().title + "` to the queue.");
			}
			
			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				Log.info("Playlist loaded");
				if(playlist.getTracks().size() < 1){
					Log.info("Playlist size is 0?");
					return;
				}
				for(AudioTrack track : playlist.getTracks()){
					addToQueue(track);
				}
				player.startTrack(playlist.getTracks().get(0), true);
				MessageUtil.sendMessage(messageChannel, ":white_check_mark: Added `" + playlist.getTracks().size() + "` tracks to the queue.");
			}
			
			@Override
			public void noMatches() {
				MessageUtil.sendMessage(messageChannel, ":x: No results for `" + identifier + "`");
			}
			
			@Override
			public void loadFailed(FriendlyException exception) {
				if(exception.severity == FriendlyException.Severity.COMMON){
					MessageUtil.sendMessage(messageChannel, ":x: " + exception.getMessage());
				}else{
					Log.info("Load failed (severity=" + exception.severity.name() + ")");
					MessageUtil.sendMessage(messageChannel, ":x: Failed to load.");
				}
			}
		});
	}
	
	@Override
	public void onPlayerPause(AudioPlayer player) {
		Log.info("Player was paused.");
	}
	
	@Override
	public void onPlayerResume(AudioPlayer player) {
		// Player was resumed
	}
	
	@Override
	public void onTrackStart(AudioPlayer player, AudioTrack track) {
		// A track started playing
	}
	
	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if (endReason.mayStartNext) {
			queue.remove(track);
			playNext();
		}
		
		
		// endReason == FINISHED: A track finished or died by an exception (mayStartNext = true).
		// endReason == LOAD_FAILED: Loading of a track failed (mayStartNext = true).
		// endReason == STOPPED: The player was stopped.
		// endReason == REPLACED: Another track started playing while this had not finished
		// endReason == CLEANUP: Player hasn't been queried for a while, if you want you can put a
		//                       clone of this back to your queue
	}
	
	@Override
	public void onTrackException(AudioPlayer player, AudioTrack track, FriendlyException exception) {
		// An already playing track threw an exception (track end event will still be received separately)
	}
	
	@Override
	public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
		// Audio track has been unable to provide us any audio, might want to just start a new track
	}
	
}
