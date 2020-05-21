package com.xenry.stagebot.audio.musicquiz.command;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.audio.IAudioInstance;
import com.xenry.stagebot.audio.command.AbstractAudioCommand;
import com.xenry.stagebot.audio.musicquiz.MusicQuizAudioInstance;
import com.xenry.stagebot.util.MessageUtil;
import net.dv8tion.jda.api.entities.*;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
@Deprecated
public class StartQuizCommand extends AbstractAudioCommand {
	
	public StartQuizCommand(AudioHandler handler){
		super(handler, "start-quiz", "startquiz");
	}
	
	@Override
	protected void perform(User user, Message message, String[] args, String label) {
		MessageChannel messageChannel = message.getChannel();
		Member member = message.getGuild().getMember(user);
		if(member == null){
			return;
		}
		GuildVoiceState voiceState = member.getVoiceState();
		if(voiceState == null){
			MessageUtil.sendMessage(messageChannel, ":x: You aren't connected to voice.");
			return;
		}
		VoiceChannel voiceChannel = voiceState.getChannel();
		if(voiceChannel == null){
			MessageUtil.sendMessage(messageChannel, ":x: You aren't connected to a voice channel.");
			return;
		}
		
		IAudioInstance instance = audioHandler.getInstance(message.getGuild());
		if(instance == null){
			instance = audioHandler.createQuizInstance(message.getGuild(), messageChannel, voiceChannel);
		}
		if(instance.isConnected()){
			MessageUtil.sendMessage(messageChannel, ":x: I'm already connected to a channel in this guild.");
			return;
		}
		if(!instance.connect()){
			MessageUtil.sendMessage(messageChannel, ":x: I don't have permission to join your voice channel.");
			return;
		}
		((MusicQuizAudioInstance)instance).start();
	}
	
}
