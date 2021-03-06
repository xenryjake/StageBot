package com.xenry.stagebot.audio.command;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.audio.AudioInstance;
import com.xenry.stagebot.audio.IAudioInstance;
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
public final class ShuffleCommand extends AbstractAudioCommand {
	
	public ShuffleCommand(AudioHandler audioHandler){
		super(audioHandler, "shuffle");
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
		
		((AudioInstance)instance).shuffleQueue();
		MessageUtil.sendMessage(messageChannel, ":twisted_rightward_arrows: Shuffled queue.");
	}
	
}
