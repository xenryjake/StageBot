package com.xenry.stagebot.util;
/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class Log {
	
	private Log() {}
	
	public static void debug(String string){
		System.out.print("[DEBUG] " + string);
	}
	
	public static void info(String string){
		System.out.println("[INFO] " + string);
	}
	
	public static void warning(String string){
		System.out.println("[WARN] " + string);
	}
	
	public static void severe(String string){
		System.out.println("[SEVERE] " + string);
	}
	
}
