package com.xenry.stagebot;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/20/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public class MongoHandler {
	
	private final StageBot stageBot;
	private final MongoClient mongo;
	private final DB db;
	
	public MongoHandler(StageBot stageBot){
		this.stageBot = stageBot;
		mongo = new MongoClient("localhost", 27017);
		db = mongo.getDB("stagebot");
	}
	
	public StageBot getStageBot() {
		return stageBot;
	}
	
	public MongoClient getMongo() {
		return mongo;
	}
	
	public DB getDB() {
		return db;
	}
	
	public DBCollection getCollection(String name){
		return db.getCollection(name);
	}
	
}
