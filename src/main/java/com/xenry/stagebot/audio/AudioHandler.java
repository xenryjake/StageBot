package com.xenry.stagebot.audio;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.xenry.stagebot.StageBot;
import com.xenry.stagebot.audio.command.*;
import com.xenry.stagebot.audio.musicquiz.MusicQuizAudioInstance;
import com.xenry.stagebot.audio.musicquiz.MusicQuizHandler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.HashMap;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class AudioHandler {
	
	public final StageBot stageBot;
	public final AudioPlayerManager manager;
	public final MusicQuizHandler musicQuizHandler;
	private final HashMap<Guild,IAudioInstance> instances;
	
	public AudioHandler(StageBot stageBot){
		this.stageBot = stageBot;
		instances = new HashMap<>();
		manager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(manager);
		
		musicQuizHandler = new MusicQuizHandler(this);
		
		stageBot.getCommandHandler().register(new ConnectCommand(this));
		stageBot.getCommandHandler().register(new DisconnectCommand(this));
		stageBot.getCommandHandler().register(new PlayCommand(this));
		stageBot.getCommandHandler().register(new PauseCommand(this));
		stageBot.getCommandHandler().register(new SkipCommand(this));
		stageBot.getCommandHandler().register(new ClearCommand(this));
		stageBot.getCommandHandler().register(new ShuffleCommand(this));
		stageBot.getCommandHandler().register(new NowPlayingCommand(this));
		stageBot.getCommandHandler().register(new QueueCommand(this));
		stageBot.getCommandHandler().register(new SeekCommand(this));
	}
	
	public HashMap<Guild,IAudioInstance> getInstances() {
		return instances;
	}
	
	public IAudioInstance getInstance(Guild guild){
		return instances.getOrDefault(guild, null);
	}
	
	public IAudioInstance createInstance(Guild guild, MessageChannel messageChannel, VoiceChannel voiceChannel){
		if(instances.containsKey(guild)){
			throw new IllegalArgumentException("An instance already exists for guild: " + guild.getName());
		}
		IAudioInstance instance = new AudioInstance(this, messageChannel, voiceChannel);
		instances.put(guild, instance);
		return instance;
	}
	
	public IAudioInstance createQuizInstance(Guild guild, MessageChannel messageChannel, VoiceChannel voiceChannel){
		if(instances.containsKey(guild)){
			throw new IllegalArgumentException("An instance already exists for guild: " + guild.getName());
		}
		IAudioInstance instance = new MusicQuizAudioInstance(musicQuizHandler, messageChannel, voiceChannel);
		instances.put(guild, instance);
		return instance;
	}
	
	public void destroyInstance(Guild guild){
		IAudioInstance instance = getInstance(guild);
		if(instance == null){
			return;
		}
		instance.disconnect();
		instance.getPlayer().destroy();
		instances.remove(guild);
	}
	
}
