package com.xenry.stagebot.audio.musicquiz;
import com.xenry.stagebot.audio.IAudioInstance;
import com.xenry.stagebot.command.CommandHandler;
import com.xenry.stagebot.util.Log;
import com.xenry.stagebot.util.MessageUtil;
import com.xenry.stagebot.util.RegexUtil;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;

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
		if(event.getAuthor().isBot() || event.getMessage().getContentRaw().startsWith(CommandHandler.PREFIX)){
			return;
		}
		IAudioInstance iInstance = musicQuizHandler.audioHandler.getInstance(event.getGuild());
		if(!(iInstance instanceof MusicQuizAudioInstance) || event.getChannel() != iInstance.getMessageChannel()){
			return;
		}
		MusicQuizAudioInstance instance = (MusicQuizAudioInstance)iInstance;
		MusicQuizSong song = instance.getCurrentSong();
		if(song == null){
			return;
		}
		String guess = event.getMessage().getContentRaw();
		boolean containsTitle = false;
		if(!instance.isCurrentTitleGuessed()){
			List<String> validTitles = song.getValidTitles();
			if(validTitles.isEmpty()){
				Log.warning("No valid titles for song " + song.getSongID());
			}else{
				for(String title : validTitles){
					if(matches(guess, title)){
						containsTitle = true;
						break;
					}
				}
			}
		}
		boolean containsArtist = false;
		if(!instance.isCurrentArtistGuessed()){
			List<String> validArtists = song.getValidArtists();
			if(validArtists.isEmpty()){
				Log.warning("No valid artists for song " + song.getSongID());
			}else{
				for(String artist : validArtists){
					if(matches(guess, artist)){
						containsArtist = true;
						break;
					}
				}
			}
		}
		if(containsTitle || containsArtist){
			MessageUtil.react(event.getMessage(), "✅");
			instance.processCorrectGuess(event.getAuthor(), containsTitle, containsArtist);
		}else{
			MessageUtil.react(event.getMessage(), "❌");
		}
	}
	
	private boolean matches(String guess, String answer){
		answer = answer.replaceAll("/","").toUpperCase();
		guess = RegexUtil.applyAlphanumericFilterUppercase(guess);
		if(RegexUtil.isValidRegex(answer)){
			return matchesRegex(guess, answer);
		}else{
			Log.info("Non-regex query: `" + answer + "`");
			return guess.contains(RegexUtil.applyAlphanumericFilterUppercase(answer));
		}
	}
	
	private boolean matchesRegex(String guess, String regex){
		guess = RegexUtil.applyAlphanumericFilter(guess);
		boolean result = guess.matches("(?s).*" + regex + ".*");
		Log.info("checking `" + guess + "` against regex `" + regex + "`: " + result);
		return result;
	}
	
}
