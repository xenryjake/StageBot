package com.xenry.stagebot.profile;
import com.mongodb.BasicDBObject;
import com.xenry.stagebot.StageBot;
import net.dv8tion.jda.api.entities.User;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/20/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class Profile extends BasicDBObject {
	
	public Profile(){
		//required for Mongo instantiation
	}
	
	public Profile(User user){
		put("discordID", user.getId());
		put("latestUserTag", user.getAsTag());
	}
	
	public String getDiscordID(){
		return getString("discordID");
	}
	
	public String getLatestUserTag(){
		return getString("latestUserTag");
	}
	
	public User getUser(){
		return StageBot.getInstance().jda.getUserById(getDiscordID());
	}
	
}
