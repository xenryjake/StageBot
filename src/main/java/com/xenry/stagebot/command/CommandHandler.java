package com.xenry.stagebot.command;
import com.xenry.stagebot.StageBot;
import com.xenry.stagebot.util.Log;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.PermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class CommandHandler extends ListenerAdapter {
	
	private static final String prefix = ";";
	
	private final StageBot stageBot;
	protected final List<Command> commands;
	
	public CommandHandler(StageBot stageBot){
		this.stageBot = stageBot;
		commands = new ArrayList<>();
	}
	
	@Override
	public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
		User user = event.getAuthor();
		if(user.isBot() || event.getChannelType() != ChannelType.TEXT){
			return;
		}
		
		Message message = event.getMessage();
		String content = message.getContentRaw();
		if(!content.startsWith(prefix)){
			return;
		}
		
		String[] split = content.substring(prefix.length()).split(" ");
		if(split.length < 1){
			return;
		}
		
		String label = split[0].toLowerCase();
		String[] args = new String[split.length - 1];
		System.arraycopy(split, 1, args, 0, split.length - 1);
		Command cmd = null;
		for(Command command : commands){
			if(command.getLabels().contains(label)){
				cmd = command;
			}
		}
		if(cmd != null){
			try{
				cmd.execute(user, message, args, label);
			}catch(Exception ex){
				MessageChannel channel = message.getChannel();
				try{
					channel.sendMessage("Failed to process command.").queue();
				}catch(PermissionException px){
					Log.warning("Couldn't send message in channel: " + channel.getName());
				}
				Log.warning("Failed to process command: " + prefix + label);
				ex.printStackTrace();
			}
		}
	}
	
	public void register(Command command){
		commands.add(command);
	}
	
}
