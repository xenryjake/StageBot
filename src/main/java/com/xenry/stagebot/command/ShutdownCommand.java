package com.xenry.stagebot.command;
import com.xenry.stagebot.StageBot;
import com.xenry.stagebot.util.MessageUtil;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class ShutdownCommand extends Command {
	
	private static final String adminTag = "Xenry#7560";
	
	public ShutdownCommand(StageBot stageBot){
		super(stageBot, "shutdown");
	}
	
	@Override
	protected void perform(User user, Message message, String[] args, String label) {
		if(!user.getAsTag().equals(adminTag)){
			return;
		}
		MessageUtil.sendMessage(message.getChannel(), "Shutting down...");
		System.out.println(user.getAsTag() + " executed bot shutdown from channel: " + message.getChannel().getName());
		System.exit(0);
	}
	
}
