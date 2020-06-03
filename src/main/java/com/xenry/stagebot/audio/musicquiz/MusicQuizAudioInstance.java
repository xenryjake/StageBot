package com.xenry.stagebot.audio.musicquiz;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.*;
import com.xenry.stagebot.audio.AudioPlayerSendHandler;
import com.xenry.stagebot.audio.IAudioInstance;
import com.xenry.stagebot.util.Log;
import com.xenry.stagebot.util.MapUtil;
import com.xenry.stagebot.util.MessageUtil;
import com.xenry.stagebot.util.Perm;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class MusicQuizAudioInstance extends AudioEventAdapter implements IAudioInstance, TrackMarkerHandler {
	
	public static final boolean TEST_MODE = false;
	
	public static final long MS_PER_SONG = 30000;
	
	private final MessageChannel messageChannel;
	private final VoiceChannel voiceChannel;
	private final MusicQuizHandler musicQuizHandler;
	private final AudioPlayer player;
	private final List<MusicQuizSong> songs;
	private final HashMap<User,Integer> scores;
	
	private int currentIndex = 0;
	private boolean currentTitleGuessed = false;
	private boolean currentArtistGuessed = false;
	
	public MusicQuizAudioInstance(MusicQuizHandler musicQuizHandler, MessageChannel messageChannel,VoiceChannel voiceChannel){
		this.musicQuizHandler = musicQuizHandler;
		this.messageChannel = messageChannel;
		this.voiceChannel = voiceChannel;
		
		songs = new ArrayList<>();
		scores = new HashMap<>();
		
		player = musicQuizHandler.audioHandler.manager.createPlayer();
		player.addListener(this);
	}
	
	@Override
	public boolean connect() {
		if(!Perm.has(voiceChannel,Permission.VOICE_CONNECT)){
			return false;
		}
		AudioManager audioManager = voiceChannel.getGuild().getAudioManager();
		audioManager.openAudioConnection(voiceChannel);
		audioManager.setSendingHandler(new AudioPlayerSendHandler(player));
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
	
	public void start(){
		MessageUtil.sendMessage(messageChannel, ":musical_note: **A music quiz is starting in a moment!**");
		
		populateQueue(15);
	}
	
	private void populateQueue(int count){
		if(count <= 0){
			throw new IllegalArgumentException("count must be greater than 0");
		}
		
		if(TEST_MODE){
			songs.clear();
			songs.addAll(musicQuizHandler.getAllSongs());
		}else{
			//List<MusicQuizSong> allSongs = TestSongListGenerator.generateA();
			List<MusicQuizSong> allSongs = musicQuizHandler.getAllSongs();
			if(allSongs.size() == 0){
				MessageUtil.sendMessage(messageChannel, ":warning: Cannot start a music quiz. There are no songs in the database.");
				musicQuizHandler.audioHandler.destroyInstance(voiceChannel.getGuild());
				return;
			}
			songs.clear();
			if(count > allSongs.size()){
				count = allSongs.size();
			}
			for(int i = 0; i < count; i++){
				MusicQuizSong song = selectRandomNoRepeats(allSongs, 0);
				if(song == null){
					continue;
				}
				songs.add(song);
			}
		}
		
		for(MusicQuizSong song : songs){
			loadSong(song);
		}
	}
	
	private MusicQuizSong selectRandomNoRepeats(List<MusicQuizSong> allSongs, int recursionDepth){
		if(recursionDepth > 10){
			return null;
		}
		MusicQuizSong song = allSongs.get(ThreadLocalRandom.current().nextInt(allSongs.size()));
		if(songs.contains(song)){
			return selectRandomNoRepeats(allSongs, ++recursionDepth);
		}
		return song;
	}
	
	public void end(){
		player.stopTrack();
		MessageUtil.sendMessage(messageChannel, ":octagonal_sign: Quiz ended.");
		musicQuizHandler.audioHandler.destroyInstance(voiceChannel.getGuild());
		for(MusicQuizSong song : songs){
			song.track = null;
		}
		songs.clear();
	}
	
	private void loadSong(MusicQuizSong song){
		musicQuizHandler.audioHandler.manager.loadItem(song.getURL(), new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack track) {
				song.track = track;
				checkIfTracksLoaded();
			}
			
			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				Log.info("Music Quiz warning: UNEXPECTED_PLAYLIST");
				songs.remove(song);
			}
			
			@Override
			public void noMatches() {
				Log.info("No Matches for music quiz song `" + song.getTitle() + "`");
				songs.remove(song);
			}
			
			@Override
			public void loadFailed(FriendlyException exception) {
				Log.info("Failed to load music quiz song `" + song.getTitle() + "` (severity=" + exception.severity.name() + "): " + exception.getMessage());
				songs.remove(song);
			}
		});
	}
	
	private void checkIfTracksLoaded(){
		if(songs.size() == 0){
			return;
		}
		for(MusicQuizSong song : songs){
			if(song.track == null){
				return;
			}
		}
		play();
	}
	
	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if (endReason.mayStartNext) {
			next();
		}
	}
	
	@Override
	public void onTrackStuck(AudioPlayer player, AudioTrack track, long thresholdMs) {
		if(thresholdMs >= 1000){
			next();
		}
	}
	
	private boolean moveToNext(){
		MusicQuizSong finishedSong = songs.get(currentIndex);
		AudioTrack finishedTrack = finishedSong.track;
		finishedTrack.setMarker(null);
		MessageUtil.sendMessage(messageChannel, ":musical_note: The song was `" + MessageUtil.stripFormatting(finishedSong.getTitle()) + "` by `" + MessageUtil.stripFormatting(finishedSong.getArtist()) + "` (" + (currentIndex+1) + "/" + songs.size() + ")");
		sendLeaderboard();
		
		currentIndex++;
		
		if(currentIndex >= songs.size()) {
			end();
			return false;
		}
		player.stopTrack();
		
		currentArtistGuessed = false;
		currentTitleGuessed = false;
		return true;
	}
	
	private void play(){
		MusicQuizSong song = songs.get(currentIndex);
		AudioTrack newTrack = song.track;
		player.playTrack(newTrack);
		newTrack.setPosition(song.getStartMS());
		newTrack.setMarker(new TrackMarker(song.getStartMS() + MS_PER_SONG, this));
		if(TEST_MODE){
			MessageUtil.sendMessage(messageChannel, "Title: `" + song.getTitle() + "`\nArtist: `" + song.getArtist() + "`");
		}
	}
	
	private void next(){
		if(moveToNext()){
			play();
		}
	}
	
	private void sendLeaderboard(){
		Map<User,Integer> sortedScores = MapUtil.sortByValueReverse(scores);
		if(sortedScores.isEmpty()){
			return;
		}
		StringBuilder sb = new StringBuilder();
		int rank = 0;
		for(Map.Entry<User,Integer> entry : sortedScores.entrySet()){
			if(++rank > 10){
				break;
			}
			switch(rank){
				case 1:
					sb.append(":first_place: ");
					break;
				case 2:
					sb.append(":second_place: ");
					break;
				case 3:
					sb.append(":third_place: ");
					break;
				default:
					sb.append(rank).append(": ");
					break;
			}
			sb.append(entry.getKey().getAsMention()).append(" : ").append(entry.getValue()).append(" points");
		}
		
		MessageUtil.sendEmbed(messageChannel, new EmbedBuilder().setTitle("Leaderboard").setColor(Color.ORANGE).setDescription(sb.toString().trim()).build());
		
	}
	
	public MusicQuizSong getCurrentSong(){
		if(currentIndex >= songs.size()){
			return null;
		}
		return songs.get(currentIndex);
	}
	
	@Override
	public void handle(MarkerState state) {
		if(state == MarkerState.REACHED){
			next();
		}
	}
	
	public void processCorrectGuess(User user, boolean title, boolean artist){
		int newScore = scores.getOrDefault(user, 0);
		int scoreMod = 0;
		if(title) {
			scoreMod++;
			currentTitleGuessed = true;
		}
		if(artist){
			scoreMod++;
			currentArtistGuessed = true;
		}
		scores.remove(user);
		scores.put(user, newScore + scoreMod);
		
		MessageUtil.sendMessage(messageChannel, user.getAsMention() + " Correct! **+" + scoreMod + " points**");
		
		if(currentTitleGuessed && currentArtistGuessed){
			next();
		}
	}
	
	public boolean isCurrentTitleGuessed() {
		return currentTitleGuessed;
	}
	
	public boolean isCurrentArtistGuessed() {
		return currentArtistGuessed;
	}
	
}
