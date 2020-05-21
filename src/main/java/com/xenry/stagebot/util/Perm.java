package com.xenry.stagebot.util;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class Perm {
	
	private Perm(){}
	
	public static boolean has(MessageChannel channel, Permission permission){
		if(!(channel instanceof TextChannel)){
			return true;
		}
		return has((TextChannel)channel, permission);
	}
	
	public static boolean has(TextChannel channel, Permission permission){
		return channel.getGuild().getSelfMember().hasPermission(permission);
	}
	
	public static boolean has(VoiceChannel channel, Permission permission){
		return channel.getGuild().getSelfMember().hasPermission(permission);
	}
	
}
