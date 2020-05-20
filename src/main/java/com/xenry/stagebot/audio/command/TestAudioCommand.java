package com.xenry.stagebot.audio.command;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.audio.AudioInstance;
import com.xenry.stagebot.util.MessageUtil;
import net.dv8tion.jda.api.entities.*;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class TestAudioCommand extends AudioCommand {
	
	public TestAudioCommand(AudioHandler audioHandler){
		super(audioHandler, "testaudio");
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
		
		AudioInstance instance = handler.createInstance(message.getGuild(), messageChannel, voiceChannel);
		instance.connect();
		instance.loadTrack("https://www.youtube.com/watch?v=rp31_j9knMI");
	}
	
}
