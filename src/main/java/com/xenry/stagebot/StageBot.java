package com.xenry.stagebot;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.command.CommandHandler;
import com.xenry.stagebot.command.PingCommand;
import com.xenry.stagebot.command.ShutdownCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class StageBot {
	
	private static StageBot instance;
	
	private final JDA jda;
	private final CommandHandler commandHandler;
	private final AudioHandler audioHandler;
	
	public static void main(String[] args) throws Exception {
		instance = new StageBot();
	}
	
	public static StageBot getInstance(){
		return instance;
	}
	
	private StageBot() throws Exception {
		jda = JDABuilder.createDefault(PrivateToken.TOKEN).build();
		
		commandHandler = new CommandHandler(this);
		jda.addEventListener(commandHandler);
		
		commandHandler.register(new PingCommand(this));
		commandHandler.register(new ShutdownCommand(this));
		
		audioHandler = new AudioHandler(this);
	}
	
	public JDA getJDA() {
		return jda;
	}
	
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}
	
	public AudioHandler getAudioHandler() {
		return audioHandler;
	}
	
}
