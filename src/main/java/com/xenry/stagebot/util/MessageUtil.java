package com.xenry.stagebot.util;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.PermissionException;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class MessageUtil {
	
	private MessageUtil(){}
	
	private static boolean has(MessageChannel channel, Permission permission){
		if(!(channel instanceof TextChannel)){
			return true;
		}
		return has((TextChannel)channel, permission);
	}
	
	private static boolean has(TextChannel channel, Permission permission){
		return channel.getGuild().getSelfMember().hasPermission(permission);
	}
	
	private static void sendMessage(MessageChannel channel, Message message){
		if(!has(channel, Permission.MESSAGE_WRITE) || !has(channel, Permission.MESSAGE_READ)){
			Log.info("No permission to send messages in channel: " + channel.getName());
			return;
		}
		channel.sendMessage(message).queue(null, null);
	}
	
	public static void sendMessage(MessageChannel channel, String message){
		sendMessage(channel, new MessageBuilder().setContent(message).build());
	}
	
	public static void sendEmbed(MessageChannel channel, MessageEmbed embed){
		if(!has(channel, Permission.MESSAGE_EMBED_LINKS)){
			Log.info("No permission to embed links in channel: " + channel.getName());
			return;
		}
		sendMessage(channel, new MessageBuilder().setEmbed(embed).build());
	}
	
	public static void sendEmbed(MessageChannel channel, String title, String description){
		sendEmbed(channel, new EmbedBuilder().setTitle(title, null).setDescription(description).build());
	}
	
	public static void deleteMessage(Message message){
		try{
			message.delete().queue();
		}catch(PermissionException ex){
			Log.info("No permission to delete message in channel: " + message.getChannel().getName());
		}
	}
	
	public static String stripFormatting(String s) {
		return s.replace("*", "\\*")
				.replace("`", "\\`")
				.replace("_", "\\_")
				.replace("~~", "\\~\\~")
				.replace(">", "\u180E>");
	}
	
	public static void react(Message message){
		//todo
	}
	
}
