package com.xenry.stagebot.audio.command;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.audio.AudioInstance;
import com.xenry.stagebot.util.MessageUtil;
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
public class PlayCommand extends AudioCommand {
	
	public PlayCommand(AudioHandler handler){
		super(handler, "play", "add", "p", "a");
	}
	
	@Override
	protected void perform(User user, Message message, String[] args, String label) {
		MessageChannel messageChannel = message.getChannel();
		Guild guild = message.getGuild();
		AudioInstance instance = handler.getInstance(guild);
		if(instance == null || !instance.isConnected()){
			MessageUtil.sendMessage(messageChannel, ":x: I'm not connected right now.");
			return;
		}
		
		if(args.length < 1){
			if(instance.getPlayer().isPaused()){
				instance.getPlayer().setPaused(false);
				MessageUtil.sendMessage(messageChannel, ":white_check_mark: Playing");
			}else{
				MessageUtil.sendMessage(messageChannel, ":x: The player is not paused.");
			}
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		for(String arg : args){
			sb.append(arg).append(" ");
		}
		String query = sb.toString().trim();
		instance.loadTrack(query);
	}
	
}
