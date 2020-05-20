package com.xenry.stagebot.util;
/**
 * StageBot created by Henry Blasingame (Xenry) on 5/19/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class TimeUtil {
	
	private TimeUtil(){}
	
	public static String getClockFromMilliseconds(long ms){
		return getClockFromSeconds((double)ms/1000);
	}
	
	public static String getClockFromSeconds(double seconds){
		if(seconds < 0){
			seconds = 0;
		}
		
		String mins = String.valueOf((int)Math.floor(seconds/60d));
		String secs = String.valueOf((long)(seconds % 60));
		
		if((seconds % 60) < 10){
			secs = "0" + secs;
		}
		return mins + ":" + secs;
	}
	
}
