package com.xenry.stagebot.audio.musicquiz.command;
import com.xenry.stagebot.audio.musicquiz.MusicQuizHandler;
import com.xenry.stagebot.util.MessageUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/20/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class MusicQuizUpdateCommand extends AbstractMusicQuizCommand {

	public MusicQuizUpdateCommand(MusicQuizHandler musicQuizHandler){
		super(musicQuizHandler, "update", "download");
		botAdminOnly = true;
	}
	
	@Override
	protected void perform(User user, Message message, String[] args, String label) {
		musicQuizHandler.download();
		MessageUtil.sendMessage(message.getChannel(), ":inbox_tray: Songs updated from database.");
	}
	
}
