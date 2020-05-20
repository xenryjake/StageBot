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

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class SeekCommand extends AudioCommand {
	
	public SeekCommand(AudioHandler audioHandler){
		super(audioHandler, "seek");
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
		if(!(instance instanceof AudioInstance)){
			MessageUtil.sendMessage(messageChannel, ":x: You can't use this command right now.");
			return;
		}
		AudioTrack track = instance.getPlayer().getPlayingTrack();
		if(track == null){
			MessageUtil.sendMessage(messageChannel, ":x: There is no track playing right now.");
			return;
		}
		if(args.length < 1){
			MessageUtil.sendMessage(messageChannel, ":x: Please specify where to seek to, in seconds.");
			return;
		}
		int seconds;
		try{
			seconds = Integer.parseInt(args[0]);
		}catch(Exception ex){
			MessageUtil.sendMessage(messageChannel, ":x: Please enter a valid integer.");
			return;
		}
		if(seconds < 0){
			MessageUtil.sendMessage(messageChannel, ":x: Please enter an integer greater than 0.");
			return;
		}
		if(seconds >= (track.getDuration()/1000)){
			MessageUtil.sendMessage(messageChannel, ":x: Please enter a number less than the length of the song.");
			return;
		}
		long newPos = ((long)seconds)*1000;
		track.setPosition(newPos);
		MessageUtil.sendMessage(messageChannel, ":white_check_mark: Seeking... " + TimeUtil.getClockFromMilliseconds(newPos) + "/" + TimeUtil.getClockFromMilliseconds(track.getDuration()));
	}
	
}
