package com.xenry.stagebot.audio.musicquiz.command;
import com.xenry.stagebot.audio.musicquiz.MusicQuizHandler;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/20/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class MusicQuizCommand extends AbstractMusicQuizCommand {
	
	public MusicQuizCommand(MusicQuizHandler musicQuizHandler){
		super(musicQuizHandler, "musicquiz", "music-quiz", "mquiz", "m-quiz", "quiz", "mq");
		addSubCommand(new MusicQuizUpdateCommand(musicQuizHandler));
		addSubCommand(new MusicQuizStartCommand(musicQuizHandler));
		addSubCommand(new MusicQuizStopCommand(musicQuizHandler));
		addSubCommand(new MusicQuizAddSongCommand(musicQuizHandler));
		addSubCommand(new MusicQuizPrintAllCommand(musicQuizHandler));
	}
	
	@Override
	protected void perform(User user, Message message, String[] args, String label) {
	
	}
	
}
