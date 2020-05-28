package com.xenry.stagebot.audio.command;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.audio.AudioInstance;
import com.xenry.stagebot.audio.IAudioInstance;
import com.xenry.stagebot.util.MessageUtil;
import com.xenry.stagebot.util.TimeUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class QueueCommand extends AbstractAudioCommand {
	
	public QueueCommand(AudioHandler audioHandler){
		super(audioHandler, "queue", "q");
	}
	
	@Override
	protected void perform(User user, Message message, String[] args, String label) {
		MessageChannel messageChannel = message.getChannel();
		Guild guild = message.getGuild();
		IAudioInstance instance = audioHandler.getInstance(guild);
		if(instance == null || !instance.isConnected()){
			MessageUtil.sendMessage(messageChannel, ":x: I'm not connected right now.");
			return;
		}
		
		if(!(instance instanceof AudioInstance)){
			MessageUtil.sendMessage(messageChannel, ":x: You can't use this command right now.");
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		List<AudioTrack> tracks = ((AudioInstance)instance).getQueue();
		int i = 0;
		for(AudioTrack track : tracks){
			if(i == 0){
				sb.append("**Now Playing:** `").append(MessageUtil.stripFormatting(track.getInfo().title)).append("`\n")
						.append(TimeUtil.getClockFromMilliseconds(track.getPosition())).append("/").append(TimeUtil.getClockFromMilliseconds(track.getDuration())).append("\n\n");
			}else{
				sb.append(i).append(". `").append(MessageUtil.stripFormatting(track.getInfo().title)).append("`\n");
			}
			if(++i > 10){
				break; //todo support multiple pages
			}
		}
		MessageUtil.sendEmbed(messageChannel, "Queue", sb.toString());
	}
	
}
