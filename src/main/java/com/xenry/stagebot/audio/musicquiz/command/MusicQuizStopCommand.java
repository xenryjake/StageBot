package com.xenry.stagebot.audio.musicquiz.command;
import com.xenry.stagebot.audio.IAudioInstance;
import com.xenry.stagebot.audio.musicquiz.MusicQuizAudioInstance;
import com.xenry.stagebot.audio.musicquiz.MusicQuizHandler;
import com.xenry.stagebot.command.CommandHandler;
import com.xenry.stagebot.util.MessageUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/20/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class MusicQuizStopCommand extends AbstractMusicQuizCommand {
	
	public MusicQuizStopCommand(MusicQuizHandler musicQuizHandler){
		super(musicQuizHandler, "stop", "end");
	}
	
	@Override
	protected void perform(User user, Message message, String[] args, String label) {
		MessageChannel messageChannel = message.getChannel();
		IAudioInstance instance = audioHandler.getInstance(message.getGuild());
		if(instance == null){
			MessageUtil.sendMessage(messageChannel, ":x: I'm not connected right now.");
			return;
		}
		if(!(instance instanceof MusicQuizAudioInstance)){
			MessageUtil.sendMessage(messageChannel, ":x: This is not a music quiz. Use `" + CommandHandler.PREFIX + "leave` to leave the channel.");
			return;
		}
		((MusicQuizAudioInstance)instance).end();
	}
	
}
