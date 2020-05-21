package com.xenry.stagebot.command;
import com.xenry.stagebot.StageBot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public abstract class Command {
	
	protected final StageBot stageBot;
	protected final List<String> labels;
	protected final List<Command> subCommands;
	
	protected boolean botAdminOnly = false;
	protected static final String adminTag = "Xenry#7560";
	
	protected Command(StageBot stageBot, String...labels){
		this.stageBot = stageBot;
		this.labels = new ArrayList<>();
		for(String lb : labels) {
			this.labels.add(lb.toLowerCase());
		}
		subCommands = new ArrayList<>();
	}
	
	public StageBot getStageBot() {
		return stageBot;
	}
	
	public List<String> getLabels() {
		return labels;
	}
	
	public List<Command> getSubCommands() {
		return subCommands;
	}
	
	protected abstract void perform(User user, Message message, String[] args, String label);
	
	public Command getSubCommand(String label){
		for(Command cmd : subCommands) {
			if(cmd.getLabels().contains(label.toLowerCase())){
				return cmd;
			}
		}
		return null;
	}
	
	protected final void addSubCommand(Command command){
		subCommands.add(command);
	}
	
	public boolean hasSubCommands(){
		return !subCommands.isEmpty();
	}
	
	public final void execute(User user, Message message, String[] args, String label){
		if(botAdminOnly && !user.getAsTag().equals(adminTag)){
			return;
		}
		if(args.length < 1 || !hasSubCommands()){
			perform(user, message, args, label);
			return;
		}
		Command sub = getSubCommand(args[0].toLowerCase());
		if(sub == null){
			perform(user, message, args, label);
		}else{
			String[] newArgs = new String[args.length - 1];
			System.arraycopy(args, 1, newArgs, 0, args.length - 1);
			sub.execute(user, message, newArgs, args[0].toLowerCase());
		}
	}
	
}
