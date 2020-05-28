package com.xenry.stagebot.profile;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.xenry.stagebot.StageBot;
import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/20/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class ProfileHandler {
	
	private final StageBot stageBot;
	private final DBCollection profileCollection;
	private final HashMap<String,Profile> profiles;
	
	public ProfileHandler(StageBot stageBot){
		this.stageBot = stageBot;
		profiles = new HashMap<>();
		profileCollection = stageBot.getMongoHandler().getCollection("profiles");
		profileCollection.setObjectClass(Profile.class);
	}
	
	public StageBot getStageBot() {
		return stageBot;
	}
	
	public Profile getProfile(User user){
		if(user == null){
			return null;
		}
		Profile profile = profiles.getOrDefault(user.getId(), null);
		if(profile == null){
			profile = (Profile) profileCollection.findOne(new BasicDBObject("discordID", user.getId()));
			if(profile == null){
				profile = new Profile(user);
			}
			profiles.put(user.getId(), profile);
			profileCollection.save(profile);
		}
		return profile;
	}
	
	public void save(Profile profile){
		profileCollection.save(profile);
	}
	
}
