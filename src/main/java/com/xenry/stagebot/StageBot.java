package com.xenry.stagebot;
import com.xenry.stagebot.audio.AudioHandler;
import com.xenry.stagebot.command.CommandHandler;
import com.xenry.stagebot.command.PingCommand;
import com.xenry.stagebot.command.ShutdownCommand;
import com.xenry.stagebot.profile.ProfileHandler;
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
	
	public final JDA jda;
	private final MongoHandler mongoHandler;
	private final CommandHandler commandHandler;
	private final ProfileHandler profileHandler;
	private final AudioHandler audioHandler;
	
	public static void main(String[] args) throws Exception {
		instance = new StageBot();
	}
	
	public static StageBot getInstance(){
		return instance;
	}
	
	private StageBot() throws Exception {
		jda = JDABuilder.createDefault(BotToken.TOKEN).build();
		
		mongoHandler = new MongoHandler(this);
		
		commandHandler = new CommandHandler(this);
		jda.addEventListener(commandHandler);
		
		commandHandler.register(new PingCommand(this));
		commandHandler.register(new ShutdownCommand(this));
		
		profileHandler = new ProfileHandler(this);
		
		audioHandler = new AudioHandler(this);
	}
	
	public JDA getJDA() {
		return jda;
	}
	
	public MongoHandler getMongoHandler() {
		return mongoHandler;
	}
	
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}
	
	public AudioHandler getAudioHandler() {
		return audioHandler;
	}
	
	public ProfileHandler getProfileHandler() {
		return profileHandler;
	}
	
}
