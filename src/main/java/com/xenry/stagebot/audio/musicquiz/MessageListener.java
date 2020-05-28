package com.xenry.stagebot.audio.musicquiz;
import com.xenry.stagebot.audio.IAudioInstance;
import com.xenry.stagebot.util.Log;
import com.xenry.stagebot.util.MessageUtil;
import com.xenry.stagebot.util.RegexUtil;
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
		if(event.getAuthor().isBot()){
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
			for(String title : song.getValidTitles()){
				if(matches(guess, title)){
					containsTitle = true;
					break;
				}
			}
		}
		boolean containsArtist = false;
		if(!instance.isCurrentArtistGuessed()){
			for(String artist : song.getValidArtists()){
				if(matches(guess, artist)){
					containsArtist = true;
					break;
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
		answer = answer.replaceAll("/","");
		guess = RegexUtil.applyAlphanumericFilterLowercase(guess);
		if(isRegexQuery(answer)){
			return matchesRegex(guess, answer);
		}else{
			return guess.contains(RegexUtil.applyAlphanumericFilterLowercase(answer));
		}
	}
	
	private boolean matchesRegex(String guess, String regex){
		guess = RegexUtil.applyAlphanumericFilter(guess);
		regex = regex.toLowerCase().substring(1, regex.length() - 1);
		boolean result = guess.matches("(?s).*" + regex + ".*");
		Log.info("checking `" + guess + "` against regex `" + regex + "`: " + result);
		return result;
	}
	
	private boolean isRegexQuery(String string){
		return RegexUtil.isValidRegex(string.substring(1, string.length() - 1));
	}
	
}
