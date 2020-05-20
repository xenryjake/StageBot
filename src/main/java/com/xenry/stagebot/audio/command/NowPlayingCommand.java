package com.xenry.stagebot.audio.command;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.audio.AudioInstance;
import com.xenry.stagebot.audio.IAudioInstance;
import com.xenry.stagebot.audio.musicquiz.MusicQuizInstance;
import com.xenry.stagebot.command.CommandHandler;
import com.xenry.stagebot.util.MessageUtil;
import com.xenry.stagebot.util.TimeUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class NowPlayingCommand extends AudioCommand {
	
	public NowPlayingCommand(AudioHandler audioHandler){
		super(audioHandler, "np", "nowplaying");
	}
	
	@Override
	protected void perform(User user, Message message, String[] args, String label) {
		MessageChannel messageChannel = message.getChannel();
		Guild guild = message.getGuild();
		IAudioInstance instance = handler.getInstance(guild);
		if(instance == null || !instance.isConnected()){
			MessageUtil.sendMessage(messageChannel, ":x: I'm not connected right now.");
			return;
		}
		
		if(instance instanceof MusicQuizInstance){
			MessageUtil.sendMessage(messageChannel, ":x: You can't look at that during a music quiz, silly!");
			return;
		}
		
		AudioTrack track = instance.getPlayer().getPlayingTrack();
		if(track == null){
			MessageUtil.sendMessage(messageChannel, ":x: There is nothing playing.");
			return;
		}
		MessageUtil.sendEmbed(messageChannel, "Now Playing", "`" + MessageUtil.stripFormatting(track.getInfo().title) + "`\n" + TimeUtil.getClockFromMilliseconds(track.getPosition()) + "/" + TimeUtil.getClockFromMilliseconds(track.getDuration()));
	}
	
}
