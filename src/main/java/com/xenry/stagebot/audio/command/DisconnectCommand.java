package com.xenry.stagebot.audio.command;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.audio.IAudioInstance;
import com.xenry.stagebot.audio.musicquiz.MusicQuizAudioInstance;
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
public final class DisconnectCommand extends AbstractAudioCommand {
	
	public DisconnectCommand(AudioHandler audioHandler){
		super(audioHandler, "disconnect", "leave");
	}
	
	@Override
	protected void perform(User user, Message message, String[] args, String label) {
		MessageChannel messageChannel = message.getChannel();
		Guild guild = message.getGuild();
		IAudioInstance instance = audioHandler.getInstance(message.getGuild());
		if(instance == null){
			MessageUtil.sendMessage(messageChannel, ":x: I'm not connected right now.");
			return;
		}
		if(instance instanceof MusicQuizAudioInstance){
			MessageUtil.sendMessage(messageChannel, ":x: A music quiz is taking place right now. Use `" + CommandHandler.PREFIX + "end-quiz` to end the quiz.");
			return;
		}
		audioHandler.destroyInstance(guild);
		MessageUtil.sendMessage(messageChannel, ":wave: Disconnected.");
	}
	
}
