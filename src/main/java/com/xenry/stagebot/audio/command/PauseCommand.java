package com.xenry.stagebot.audio.command;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.audio.AudioInstance;
import com.xenry.stagebot.audio.IAudioInstance;
import com.xenry.stagebot.audio.musicquiz.MusicQuizInstance;
import com.xenry.stagebot.command.CommandHandler;
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
public class PauseCommand extends AudioCommand {
	
	public PauseCommand(AudioHandler audioHandler){
		super(audioHandler, "pause");
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
			MessageUtil.sendMessage(messageChannel, ":x: A music quiz is taking place right now. Use `" + CommandHandler.PREFIX + "end-quiz` to end the quiz.");
			return;
		}
		
		if(instance.getPlayer().isPaused()){
			MessageUtil.sendMessage(messageChannel, ":x: The player is not playing.");
		}else{
			instance.getPlayer().setPaused(true);
			MessageUtil.sendMessage(messageChannel, ":pause_button: Paused");
		}
	}
	
}
