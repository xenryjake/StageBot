package com.xenry.stagebot.util;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * StageBot created by Henry Blasingame (Xenry) on 5/27/20
 * The content in this file and all related files are
 * Copyright (C) 2020 Henry Blasingame.
 * Usage of this content without written consent of Henry Blasingame
 * is prohibited.
 */
public final class RegexUtil {
	
	private RegexUtil(){}
	
	public static boolean isValidRegex(String pattern) {
		try{
			Pattern.compile(pattern);
		}catch(PatternSyntaxException exception) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args){
		String regex = "RUDEBO[IY]";
		String test = "RUDEBOY";
		System.out.println("REGEX: " + regex);
		System.out.println("VALID: " + isValidRegex(regex));
		System.out.println("TEST: " + test);
		System.out.println("FILT: " + test.replaceAll("(\\[\"|\",\"|\"])", ""));
		System.out.println("RESULT: " + applyAlphanumericFilter(test.toLowerCase()).matches(regex.toLowerCase()));
	}
	
	public static String applyAlphanumericFilter(String string){
		return string.replaceAll("[^a-zA-Z0-9]", "");
	}
	
	public static String applyAlphanumericFilterLowercase(String string){
		return string.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
	}
	
	public static String applyAlphanumericFilterUppercase(String string){
		return string.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
	}
	
}
